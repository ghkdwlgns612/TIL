package com.example.querydslbasic;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class Controller {

    private JPAQueryFactory jpaQueryFactory;
    private MemberRepository memberRepository;
    private TeamRepository teamRepository;
    QMember qMember = new QMember("abc");

    @Autowired
    public Controller(JPAQueryFactory jpaQueryFactory, MemberRepository memberRepository, TeamRepository teamRepository) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping("/save")
    public void saveController() {
        Team team = new Team("team",12345);
        Member member1 = new Member("jihun","soccer",team);
        Member member2 = new Member("jihuhwan","basketball",team);
        Member member3 = new Member("hwang","baseball", team);

        teamRepository.save(team);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
    }

    @GetMapping("/query")
    public void queryController() {
        Member member = jpaQueryFactory.selectFrom(qMember).fetchFirst();
        log.info("{}", member.getMemberName());
    }

    @GetMapping("/multi") //http://localhost:8080/multi?pageNumber=2&size=1&name=jihun&department=soccer
    public void multiController(Integer pageNumber, Integer size, String name, String department) {
        List<Member> fetch = jpaQueryFactory
                .selectFrom(qMember)
                .where(eqMemberName(name), eqDepartment(department))
                .offset(pageNumber)
                .limit(size)
                .fetch();
    }
    private BooleanExpression eqDepartment(String department) {
        if (ObjectUtils.isEmpty(department))
            return null;
        return qMember.department.eq(department);
    }
    private BooleanExpression eqMemberName(String name) {
        if (ObjectUtils.isEmpty(name))
            return null;
        return qMember.memberName.eq(name);
    }
}
