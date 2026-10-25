package com.example.saturday.repository;

import com.example.saturday.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public  interface MemberRepository extends JpaRepository<Member,Long> {

    boolean existsByEmail(String email);
     Optional<Member> findByEmail(String email);
    List<Member> findByRole_Name(String roleName);
}
