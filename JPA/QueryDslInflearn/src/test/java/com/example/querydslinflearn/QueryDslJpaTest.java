package com.example.querydslinflearn;

import com.example.querydslinflearn.dto.MemberSearchCondition;
import com.example.querydslinflearn.dto.MemberTeamDto;
import com.example.querydslinflearn.entity.Member;
import com.example.querydslinflearn.entity.Team;
import com.example.querydslinflearn.repository.MemberJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class QueryDslJpaTest {
    @Autowired
    EntityManager em;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void basicTest() {
        Member member = new Member("member1",10);
        memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> allMembers = memberJpaRepository.findAll();
        assertThat(allMembers).containsExactly(member);

        List<Member> findUsername = memberJpaRepository.findByUsername("member1");
        assertThat(findUsername).containsExactly(member);
    }

    @Test
    public void basicQueryDslTest() {
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        List<Member> result1 = memberJpaRepository.findAllUseQueryDsl();
        assertThat(result1).containsExactly(member);

        List<Member> result2 = memberJpaRepository.findByUsernameUseQueryDsl("member1");
        assertThat(result2).containsExactly(member);
    }

    @Test
    public void searchTest() {
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

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(condition);

        assertThat(result).extracting("username").containsExactly("member4");
    }
}
