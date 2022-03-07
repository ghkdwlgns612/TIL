package com.example.querydslinflearn;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
}
