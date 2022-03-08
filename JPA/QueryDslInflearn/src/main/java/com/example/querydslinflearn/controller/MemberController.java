package com.example.querydslinflearn.controller;

import com.example.querydslinflearn.MemberJpaRepository;
import com.example.querydslinflearn.dto.MemberSearchCondition;
import com.example.querydslinflearn.dto.MemberTeamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberJpaRepository memberJpaRepository;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCondition condition) {
        return memberJpaRepository.search(condition);
    }
}
