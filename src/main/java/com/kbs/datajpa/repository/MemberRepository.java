package com.kbs.datajpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.kbs.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
  List<Member> findByUserNameAndAgeGreaterThan(String userName, int age);
}
