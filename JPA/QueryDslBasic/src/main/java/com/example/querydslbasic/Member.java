package com.example.querydslbasic;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String memberName;

    private String department;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Member() {
    }

    public Member(String memberName, String department, Team team) {
        this.memberName = memberName;
        this.department = department;
        this.team = team;
    }
}
