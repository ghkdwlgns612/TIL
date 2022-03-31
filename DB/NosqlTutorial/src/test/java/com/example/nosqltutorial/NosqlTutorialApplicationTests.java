package com.example.nosqltutorial;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class NosqlTutorialApplicationTests {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void saveTest() {
		Member member1 = new Member(1L,"hun");
		Member member2 = new Member(2L,"jin");
		memberRepository.insert(member1);
		memberRepository.insert(member2);

	}

	@Test
	void findAllTest() {
		List<Member> members = memberRepository.findAll();

		Assertions.assertThat(members.size()).isEqualTo(2);
	}

}
