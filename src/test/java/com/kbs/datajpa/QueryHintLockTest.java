package com.kbs.datajpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.kbs.datajpa.entity.Member;
import com.kbs.datajpa.repository.MemberRepository;

@SpringBootTest
@Transactional
class QueryHintLockTest {

  @PersistenceContext
  private EntityManager em;
  
  @Autowired
  private MemberRepository memberRepository;
  
  @Test
  void queryHintReadOnly() {
    
    //given
    memberRepository.save(new Member("member1", 10));
    em.flush();
    em.clear();
    
    //when
    Member member = memberRepository.findReadOnlyByUserName("member1");
    member.setUserName("member2");
    
    em.flush(); //Update Query 미실행
    
    //일반 find 메소드로 조회한 경우 더티체킹으로 인해 객체가 변경되면 db에도 update가 되는데
    //Read Only 힌트를 준 메소드로 조회한 경우는 update가 되지 않음.
  }

  @Test
  void queryHintLock() {
    // given
    memberRepository.save(new Member("member1", 10));
    em.flush();
    em.clear();

    // when
    List<Member> members = memberRepository.findHintLockByUserName("member1");
    // select for update 쿼리가 실행되어 다른 세션에서 해당 data에 접근하지 못하도록 함 
  }
}
