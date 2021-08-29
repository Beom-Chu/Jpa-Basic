package com.kbs.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kbs.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

}
