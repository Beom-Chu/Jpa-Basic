package com.kbs.datajpa;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.kbs.datajpa.entity.Member;
import com.kbs.datajpa.entity.Team;
import com.kbs.datajpa.repository.MemberRepository;
import com.kbs.datajpa.repository.TeamRepository;

@SpringBootTest
@Transactional
class AuditingTest {

  @Autowired
  private TeamRepository teamRepository;
  
  @Autowired
  private MemberRepository memberRepository;
  
  @Autowired
  private EntityManager em;
  
  @Test
  @DisplayName("순서 JPA 사용으로 Auditing Test")
  void JpaBaseEntityTest() throws Exception {
    
    //given
    Team team = new Team("teamA");
    teamRepository.save(team);  //@PrePersist

    Thread.sleep(500);
    team.setName("TeamB");
    
    em.flush(); //@PreUpdate
    em.clear();
    
    //when
    Team findTeam = teamRepository.findById(team.getId()).get();
    
    //then
    System.out.println("[[[findTeam.CreatedDate : "+findTeam.getCreatedDate());
    System.out.println("[[[findTeam.UpdatedDate : "+findTeam.getUpdatedDate());
  }

  
  @Test
  @DisplayName("스프링 데이터 Auditing 적용 Test")
  void BaseEntityTest() throws Exception {
    
    //given
    Member member = new Member("member1", 10);
    memberRepository.save(member);  //@PrePersist

    Thread.sleep(500);
    member.setUserName("member2");
    
    em.flush(); //@PreUpdate
    em.clear();
    
    //when
    Member findMember = memberRepository.findById(member.getId()).get();
    
    //then
    System.out.println("[[[findMember.CreatedDate : "+findMember.getCreatedDate());
    System.out.println("[[[findMember.LastModifiedDate : "+findMember.getLastModifiedDate());
  }
}
