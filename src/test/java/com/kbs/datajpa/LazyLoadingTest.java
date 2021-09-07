package com.kbs.datajpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import com.kbs.datajpa.entity.Member;
import com.kbs.datajpa.entity.Team;
import com.kbs.datajpa.repository.MemberRepository;
import com.kbs.datajpa.repository.TeamRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
//@Rollback(false)
class LazyLoadingTest {

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private TeamRepository teamRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Test
  @Order(1)
  void findMemberLazy() {

    // given
    Team teamA = new Team("teamA");
    Team teamB = new Team("teamB");
    teamRepository.save(teamA);
    teamRepository.save(teamB);
    memberRepository.save(new Member("member1", 10, teamA));
    memberRepository.save(new Member("member2", 20, teamB));

    em.flush();
    em.clear();

    // when
    List<Member> members = memberRepository.findAll();

    // then
    // N+1 문제 발생!!
    for (Member member : members) {
      System.out.println("[[[1]member.name" + member.getUserName());
      System.out.println("[[[1]member.team.name" + member.getTeam().getName());
    }
  }

  @Test
  @Order(2)
  void findMemberLazy2() {

    // given
    Team teamA = new Team("teamA");
    Team teamB = new Team("teamB");
    teamRepository.save(teamA);
    teamRepository.save(teamB);
    memberRepository.save(new Member("member1", 10, teamA));
    memberRepository.save(new Member("member2", 20, teamB));
    memberRepository.save(new Member("member3", 10, teamA));

    em.flush();
    em.clear();

    // when
    List<Member> members = memberRepository.findMemberFetchJoin();

    // then
    // N+1 문제 해결!!
    for (Member member : members) {
      System.out.println("[[[2]member.name" + member.getUserName());
      System.out.println("[[[2]member.team.name" + member.getTeam().getName());
    }
    
    em.clear();
    
    // when
    members = memberRepository.findAll();

    // then
    // N+1 문제 해결!!
    for (Member member : members) {
      System.out.println("[[[3]member.name" + member.getUserName());
      System.out.println("[[[3]member.team.name" + member.getTeam().getName());
    }
    
    em.clear();
    
    // when
    members = memberRepository.findMemberEntityGraph();

    // then
    // N+1 문제 해결!!
    for (Member member : members) {
      System.out.println("[[[4]member.name" + member.getUserName());
      System.out.println("[[[4]member.team.name" + member.getTeam().getName());
    }
    
    em.clear();
    
    // when
    members = memberRepository.findEntityGraphByAge(10);

    // then
    // N+1 문제 해결!!
    for (Member member : members) {
      System.out.println("[[[5]member.name" + member.getUserName());
      System.out.println("[[[5]member.team.name" + member.getTeam().getName());
    }
  }
  
}
