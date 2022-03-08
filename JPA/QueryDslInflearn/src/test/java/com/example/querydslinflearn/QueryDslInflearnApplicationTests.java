package com.example.querydslinflearn;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
class QueryDslInflearnApplicationTests {

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory jpaQueryFactory;

    @BeforeEach
    public void init() {
        jpaQueryFactory = new JPAQueryFactory(em);

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    void contextLoads() {
        Users users = new Users();
        em.persist(users);

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QUsers qUsers = QUsers.users;
        Users result = jpaQueryFactory.selectFrom(qUsers).fetchOne();

        Assertions.assertThat(result).isEqualTo(users);
    }

    @Test
    void startJpql() {
        Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();
        Assertions.assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void startQuerydsl() {
        QMember m = QMember.member;

        Member member = jpaQueryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("member1"))
                .fetchOne();
        Assertions.assertThat(member.getUsername()).isEqualTo("member1");
    }

    @Test
    void search() {
        QMember m = QMember.member;

        Member result = jpaQueryFactory
                .selectFrom(m)
                .from(m)
                .where(m.username.eq("member1").and(m.age.between(10,30)))
                .fetchOne();
        Assertions.assertThat(result.getUsername()).isEqualTo("member1");
        Assertions.assertThat(result.getAge()).isEqualTo(10);
    }

    @Test
    void searchAndParam() {
        QMember m = QMember.member;

        Member result = jpaQueryFactory
                .selectFrom(m)
                .from(m)
                .where(
                        m.username.eq("member1"),
                        m.age.between(10,30)
                )
                .fetchOne();
        Assertions.assertThat(result.getUsername()).isEqualTo("member1");
        Assertions.assertThat(result.getAge()).isEqualTo(10);
    }

    @Test
    void resultFetch() {
        QMember m = QMember.member;

        List<Member> member1 = jpaQueryFactory.selectFrom(m).fetch();
//        Member member2 = jpaQueryFactory.selectFrom(m).fetchOne();
        Member member3 = jpaQueryFactory.selectFrom(m).fetchFirst();
        List<Member> member4 = jpaQueryFactory.selectFrom(m).fetchResults().getResults(); //page + 전체로 함께가져오는 기능을함. 쿼리가 2번 나간다.

    }

    @Test
    void sort() {
        QMember m = QMember.member;
        em.persist(new Member(null,100));
        em.persist(new Member("member5",100));
        em.persist(new Member("member6",100));

        List<Member> result = jpaQueryFactory
                .selectFrom(m)
                .where(m.age.eq(100))
                .orderBy(m.age.desc(), m.username.asc().nullsLast())
                .fetch();

        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);
        Assertions.assertThat(member5.getUsername()).isEqualTo("member5");
        Assertions.assertThat(member6.getUsername()).isEqualTo("member6");
        Assertions.assertThat(memberNull.getUsername()).isNull();
    }

    @Test
    void paging1() {
        QMember m = QMember.member;

        List<Member> result = jpaQueryFactory
                .selectFrom(m)
                .orderBy(m.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void paging2() {
        QMember m = QMember.member;

        QueryResults<Member> result = jpaQueryFactory
                .selectFrom(m)
                .orderBy(m.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();

        Assertions.assertThat(result.getTotal()).isEqualTo(4);
        Assertions.assertThat(result.getOffset()).isEqualTo(1);
        Assertions.assertThat(result.getLimit()).isEqualTo(2);
        Assertions.assertThat(result.getResults().size()).isEqualTo(2);
    }
    @Test
    public void group() throws Exception {
        QMember m = QMember.member;
        QTeam t = QTeam.team;

        List<Tuple> result = jpaQueryFactory.select(t.name, m.age.avg())
                .from(m)
                .join(m.team, t)
                .groupBy(t.name)
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        Assertions.assertThat(teamA.get(t.name)).isEqualTo("teamA");
        Assertions.assertThat(teamA.get(m.age.avg())).isEqualTo(15);

        Assertions.assertThat(teamB.get(t.name)).isEqualTo("teamB");
        Assertions.assertThat(teamB.get(m.age.avg())).isEqualTo(35);
    }

    @Test
    public void join() throws Exception {
        QMember m = QMember.member;
        QTeam t = QTeam.team;

        List<Member> result = jpaQueryFactory
                .selectFrom(m)
                .join(m.team, t)
                .where(t.name.eq("teamA"))
                .fetch();

        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void theta_join() throws Exception {
        QMember m = QMember.member;
        QTeam t = QTeam.team;

        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        List<Member> result = jpaQueryFactory.select(m)
                .from(m, t)
                .where(m.username.eq(t.name))
                .fetch();
        Assertions.assertThat(result).extracting("username").containsExactly("teamA","teamB");
    }

    @Test
    void join_on() {
        QMember m = QMember.member;
        QTeam t = QTeam.team;

        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        List<Tuple> result = jpaQueryFactory
                .select(m, t)
                .from(m)
                .leftJoin(m.team, t).on(t.name.eq("teamA"))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("t=" + tuple);
        }
    }

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    void fetch_join_no() {
        em.flush();
        em.clear();

        QMember m = QMember.member;
        QTeam t = QTeam.team;

        Member findMember = jpaQueryFactory.selectFrom(m)
                .where(m.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());

        Assertions.assertThat(loaded).as("패치 미적용").isFalse();
    }

    @Test
    void fetch_join_use() {
        em.flush();
        em.clear();

        QMember m = QMember.member;
        QTeam t = QTeam.team;

        Member findMember = jpaQueryFactory.selectFrom(m)
                .join(m.team, t).fetchJoin()
                .where(m.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());

        Assertions.assertThat(loaded).as("패치 조인 적용").isTrue();
    }

    /*
    * 나이가 가장많은 회원조회
    * */
    @Test
    public void subQuery() throws Exception {
        QMember m = QMember.member;
        QMember mSub = new QMember("memberSub");
        QTeam t = QTeam.team;

        List<Member> result = jpaQueryFactory.selectFrom(m).where(
                        m.age.eq(
                                JPAExpressions
                                        .select(mSub.age.max())
                                        .from(mSub)
                        ))
                .fetch();

        Assertions.assertThat(result).extracting("age").containsExactly(40);
    }

    @Test
    public void avgQuery() throws Exception {
        QMember m = QMember.member;
        QMember mSub = new QMember("memberSub");

        List<Member> result = jpaQueryFactory.selectFrom(m).where(
                        m.age.goe(
                                JPAExpressions
                                        .select(mSub.age.avg())
                                        .from(mSub)
                        ))
                .fetch();

        Assertions.assertThat(result).extracting("age").containsExactly(30,40);
    }

    @Test
    public void subInQuery() throws Exception {
        QMember m = QMember.member;
        QMember mSub = new QMember("memberSub");

        List<Member> result = jpaQueryFactory.selectFrom(m).where(
                        m.age.eq(
                                JPAExpressions
                                        .select(mSub.age)
                                        .from(mSub)
                                        .where(mSub.age.gt(10))
                        ))
                .fetch();

        Assertions.assertThat(result).extracting("age").containsExactly(20,30,40);
    }

    @Test
    public void selectSubQuery() throws Exception {
        QMember m = QMember.member;
        QMember mSub = new QMember("memberSub");

        List<Tuple> result = jpaQueryFactory.select(m.username,
                        JPAExpressions.select(mSub.age.avg())
                                .from(mSub))
                .from(m)
                .fetch();

        for (Tuple t : result) {
            System.out.println("tuple = " + t);
        }
    }

    @Test
    public void basicCase() throws Exception {
        QMember m = QMember.member;

        List<String> result = jpaQueryFactory.select(m.age
                .when(10).then("열살")
                .when(20).then("스무")
                .otherwise("기타")
        )
                .from(m)
                .fetch();

        for (String str : result)
            System.out.println("result : " + str);
    }

    @Test
    public void complexCase() throws Exception {
        QMember m = QMember.member;

        List<String> result = jpaQueryFactory.select(new CaseBuilder()
                        .when(m.age.between(0, 20)).then("0~20살")
                        .when(m.age.between(21, 30)).then("21~30살")
                        .otherwise("31살 이상")
                )
                .from(m)
                .fetch();

        for (String str : result)
            System.out.println("result : " + str);
    }


    @Test
    public void constant() throws Exception {
        QMember m = QMember.member;

        List<Tuple> result = jpaQueryFactory
                .select(m.username, Expressions.constant("A"))
                .from(m)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println(tuple);
        }
    }


    @Test
    public void concat() throws Exception {
        QMember m = QMember.member;

        List<String> result = jpaQueryFactory
                .select(m.username.concat("_").concat(m.age.stringValue()))
                .from(m)
                .where(m.username.eq("member1"))
                .fetch();

        for (String s : result) {
            System.out.println("result = " + s);
        }
    }
}
