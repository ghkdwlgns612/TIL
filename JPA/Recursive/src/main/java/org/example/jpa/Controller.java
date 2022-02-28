package org.example.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Controller {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public Controller(MemberRepository memberRepository, TeamRepository teamRepository) {
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping
    public Member testController() throws InterruptedException {
        Team team = new Team("team");
        Member member1 = new Member("hwang",team);
        Member member2 = new Member("jihun",team);

        teamRepository.save(team);

        memberRepository.save(member1);
        memberRepository.save(member2);


        return memberRepository.findById(2L).orElseThrow(NullPointerException::new);
    }

    @GetMapping("/reteam")
    public Team reTeamController() {
        return teamRepository.findById(1L).orElseThrow(NullPointerException::new);
    }

    @GetMapping("/team")
    public TeamResponseDto testTeamController() {
        Team team = teamRepository.findById(1L).orElseThrow(NullPointerException::new);
        TeamResponseDto response = makeResponse(team);
        return response;
    }

    private TeamResponseDto makeResponse(Team team) {
//        List<String> names = team.getMembers().stream().map(Member::getMemberName).collect(Collectors.toList());
        List<Member> members = team.getMembers().stream().collect(Collectors.toList());
        return new TeamResponseDto(team.getTeamName(),members);
    }
}
