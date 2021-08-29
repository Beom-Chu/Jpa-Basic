package com.kbs.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import com.kbs.datajpa.entity.Member;

@SpringBootTest
@Transactional // 테스트에서 @Transactional 사용시 테스트 후 DB 롤백 처리됨 [롤백되지 않게 하려면 @Rollback(false) 사용]
@Rollback(false)
class MemberJpaRepositoryTest {

  @Autowired
  MemberJpaRepository memberJpaRepository;
  
  @Test
  void testMember() {
    Member member = new Member("memberA");
    Member savedMember = memberJpaRepository.save(member);
    
    Member findMember = memberJpaRepository.find(savedMember.getId());
    
    assertThat(findMember.getId()).isEqualTo(member.getId());
    assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
    assertThat(findMember).isEqualTo(member);
  }

}
