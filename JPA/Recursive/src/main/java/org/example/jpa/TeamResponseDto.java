package org.example.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class TeamResponseDto {
    private String teamName;
    private List<Member> memberName;

    public TeamResponseDto(String teamName, List<Member> memberName) {
        this.teamName = teamName;
        this.memberName = memberName;
    }
}
