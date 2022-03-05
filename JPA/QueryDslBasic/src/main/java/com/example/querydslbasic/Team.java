package com.example.querydslbasic;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String teamName;

    private Integer teamNumber;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public Team() {
    }

    public Team(String teamName, Integer teamNumber) {
        this.teamName = teamName;
        this.teamNumber = teamNumber;
    }
}
