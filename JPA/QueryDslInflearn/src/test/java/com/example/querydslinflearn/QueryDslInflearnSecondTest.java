package com.example.querydslinflearn;

import com.example.querydslinflearn.dto.MemberDto;
import com.example.querydslinflearn.dto.QMemberDto;
import com.example.querydslinflearn.entity.Member;
import com.example.querydslinflearn.entity.Team;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import static com.example.querydslinflearn.entity.QMember.member;

@SpringBootTest
@Transactional
public class QueryDslInflearnSecondTest {

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
    public void simpleProjection() throws Exception {
        List<String> result = jpaQueryFactory
                .select(member.username)
                .from(member)
                .fetch();
        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void tupleProjection() throws Exception {
        List<Tuple> result = jpaQueryFactory.select(member.username, member.age)
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            String username = tuple.get(member.username);
            Integer age = tuple.get(member.age);
            System.out.println("username = " + username);
            System.out.println("age = " + age);
        }
    }

    @Test
    public void findDtoByJpql() throws Exception {
        List<MemberDto> result = em.createQuery("select new com.example.querydslinflearn.dto.MemberDto(m.username, m.age) from Member m"
                , MemberDto.class)
                .getResultList();

        for (MemberDto memberDto : result) {
            System.out.println(memberDto);
        }
    }

    @Test //기본 생성자 꼭 필요
    public void findByDtoSetter() throws Exception {
        List<MemberDto> result = jpaQueryFactory.select(Projections.bean(MemberDto.class, //세터를 통해서 값이 들어감
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println(memberDto);
        }
    }

    @Test //기본 생성자 꼭 필요
    public void findByDtoField() throws Exception {
        List<MemberDto> result = jpaQueryFactory.select(Projections.fields(MemberDto.class, //필드에 바로 꼽아넣음
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println(memberDto);
        }
    }

    @Test //기본 생성자 꼭 필요
    public void findByDtoConstructor() throws Exception {
        List<MemberDto> result = jpaQueryFactory.select(Projections.constructor(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println(memberDto);
        }
    }

    @Test //생성자 인젝션과 다른점은 complie할 때 오류를 잡아준다. 문제는 Dto자체가 QueryDsl에 의존성을 가지게된다..Dto는 여러 Layer를 쓰는데 영향을 받으면 안된다..
    public void findDtoByQueryInjection() throws Exception {
        List<MemberDto> result = jpaQueryFactory
                .select(new QMemberDto(member.username, member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println(memberDto);
        }
    }

    @Test
    public void dynamicQuery_BooleanBuilder() {
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember(usernameParam, ageParam);
    }

    private List<Member> searchMember(String username,Integer age) {

        BooleanBuilder builder = new BooleanBuilder();
        if (username != null) {
            builder.and(member.username.eq(username));
        }
        if (age != null) {
            builder.and(member.age.eq(age));
        }

        return  jpaQueryFactory
                    .selectFrom(member)
                    .where(builder)
                    .fetch();
    }

    @Test
    public void dynamicQuery_WhereParam() throws Exception {
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember2(usernameParam, ageParam);

    }

    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return jpaQueryFactory
                .selectFrom(member)
                .where(usernameEq(usernameCond), ageEq(ageCond))
                .fetch();
    }
    private Predicate usernameEq(String usernameCond) {
        return usernameCond == null ? null : member.username.eq(usernameCond);
    }
    private Predicate ageEq(Integer ageCond) {
        return ageCond == null ? null : member.age.eq(ageCond);
    }

    @Test //bulk연산을 할 경우 1차캐시와 DB의 데이터가 맞지 않는다. 그래서 항상 em.flush(), em.clear()를 해주고 조회하자.(Non-RepeatableRead)
    public void bulkUpdate() throws Exception {
        // member1 = 10 -> DB member1
        // member2 = 20 -> DB member2
        // member3 = 30 -> DB member3
        // member4 = 40 -> DB member4

        long count = jpaQueryFactory
                .update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(28))
                .execute();

        em.flush();
        em.clear();

        List<Member> result = jpaQueryFactory.selectFrom(member).fetch();
        // 업데이트 후 DB
        // 1 member1 = 10 -> DB 비회원
        // 2 member2 = 20 -> DB 비회원
        // 3 member3 = 30 -> DB member3
        // 4 member4 = 40 -> DB member4

        // 업데이트 후 1차 캐시
        // member1 = 10 -> DB member1
        // member2 = 20 -> DB member2
        // member3 = 30 -> DB member3
        // member4 = 40 -> DB member4
    }

    @Test
    public void bulkAdd() throws Exception {
        jpaQueryFactory
                .update(member)
                .set(member.age, member.age.add(1))
                .execute();
    }

    @Test
    public void bulkDelete() throws Exception {
        jpaQueryFactory
                .delete(member)
                .where(member.age.gt(18))
                .execute();
    }

    @Test
    public void sqlFunction() throws Exception {
        List<String> result = jpaQueryFactory
                .select(Expressions.stringTemplate("function('replace', {0}, {1}, {2})",
                        member.username, "member", "M"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println(s);
        }
    }

    @Test
    public void sqlFunction2() {
        List<String> result = jpaQueryFactory
                .select(member.username)
                .from(member)
                .where(member.username.eq(Expressions.stringTemplate("function('lower', {0})", member.username)))
                .fetch();
        for (String s : result) {
            System.out.println(s);
        }
    }
}
