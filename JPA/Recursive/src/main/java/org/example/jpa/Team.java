package org.example.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue
    private Long teamId;

    @Column(length = 30)
    private String teamName;

    @OneToMany(mappedBy="team", fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();

    public Team(String teamName) {
        this.teamName = teamName;
    }
}
