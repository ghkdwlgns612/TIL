package com.example.nosqltutorial;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MemberRepository extends MongoRepository<Member, Long> {
    List<Member> findAll();
}
