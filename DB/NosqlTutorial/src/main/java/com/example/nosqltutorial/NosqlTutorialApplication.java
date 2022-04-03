package com.example.nosqltutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class NosqlTutorialApplication implements CommandLineRunner {

	private MemberRepository memberRepository;

	@Autowired
	public NosqlTutorialApplication(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(NosqlTutorialApplication.class, args);
	}

	@Override
	public void run(String... args) {
		memberRepository.deleteAll();

		memberRepository.save(new Member(1L,"jihuhwan"));
		memberRepository.save(new Member(2L, "jihun"));

		for (Member m : memberRepository.findAll()) {
			System.out.println(m.getName());
		}
	}
}
