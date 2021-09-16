package com.kbs.datajpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import com.kbs.datajpa.entity.Member;
import com.kbs.datajpa.entity.Team;
import com.kbs.datajpa.repository.MemberGrouping;
import com.kbs.datajpa.repository.MemberRepository;

@SpringBootTest
@Transactional
@Commit
class GroupingTest {

  @PersistenceContext
  private EntityManager em;
  
  @Autowired
  private MemberRepository memberRepository;
  
  @Test
  void test() {
    
    Team teamA = new Team("teamA");
    Team teamB = new Team("teamB");
    em.persist(teamA);
    em.persist(teamB);
    
    Member member1 = new Member("member1", 10, teamA);
    Member member2 = new Member("member2", 15, teamA);
    Member member3 = new Member("member3", 20, teamA);
    Member member4 = new Member("member4", 10, teamB);
    Member member5 = new Member("member5", 20, teamB);
    em.persist(member1);
    em.persist(member2);
    em.persist(member3);
    em.persist(member4);
    em.persist(member5);
    
    em.flush();
    em.clear();
    
    List<MemberGrouping> findGrouping = memberRepository.findGrouping();
    for(MemberGrouping member : findGrouping) {
      System.out.println("[[[[["+member.getName()+","+member.getCount());
    }
    
  }

}
