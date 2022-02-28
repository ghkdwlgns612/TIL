package org.example.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Long memberId;

    private String memberName;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String memberName, Team team) {
        this.memberName = memberName;
        this.team = team;
    }
}
