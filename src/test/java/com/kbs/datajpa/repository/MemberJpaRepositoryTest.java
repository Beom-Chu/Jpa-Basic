package com.kbs.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import com.kbs.datajpa.entity.Member;

@SpringBootTest
@Transactional // 테스트에서 @Transactional 사용시 테스트 후 DB 롤백 처리됨 [롤백되지 않게 하려면 @Rollback(false) 사용]
//@Rollback(false)
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

  @Test
  public void basicCRUD() {
    
    Member member1 = new Member("member1");
    Member member2 = new Member("member2");

    memberJpaRepository.save(member1);
    memberJpaRepository.save(member2);
    
    Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
    Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
    
    assertThat(findMember1).isEqualTo(member1);
    assertThat(findMember2).isEqualTo(member2);
    
    List<Member> all = memberJpaRepository.findAll();
    assertThat(all.size()).isEqualTo(2);
    
    long count = memberJpaRepository.count();
    assertThat(count).isEqualTo(2);
    
    memberJpaRepository.delete(member1);
    memberJpaRepository.delete(member2);
    
    long deletedCount = memberJpaRepository.count();
    assertThat(deletedCount).isEqualTo(0);
    
  }
  
  @Test
  public void findByUserNameAndAgeGreaterThan() {
    
    Member m1 = new Member("AAA",10);
    Member m2 = new Member("AAA",20);
    
    memberJpaRepository.save(m1);
    memberJpaRepository.save(m2);
    
    List<Member> result = memberJpaRepository.findByUserNameAndAgeGreaterThan("AAA", 15);
    
    assertThat(result.get(0).getUserName()).isEqualTo("AAA");
    assertThat(result.get(0).getAge()).isEqualTo(20);
    assertThat(result.size()).isEqualTo(1);
    
  }
  
  @Test
  public void testNamedQuery() {
    Member m1 = new Member("AAA",10);
    Member m2 = new Member("BBB",20);
    
    memberJpaRepository.save(m1);
    memberJpaRepository.save(m2);
    
    List<Member> result = memberJpaRepository.findByUserName("AAA");
    
    assertThat(result.get(0).getUserName()).isEqualTo("AAA");
  }
  
  @Test
  public void paging() {
    
    //given
    memberJpaRepository.save(new Member("member1", 10));
    memberJpaRepository.save(new Member("member2", 10));
    memberJpaRepository.save(new Member("member3", 10));
    memberJpaRepository.save(new Member("member4", 10));
    memberJpaRepository.save(new Member("member5", 10));
    
    int age = 10;
    int offset = 0;
    int limit = 3;
    
    //when
    List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
    long totalCount = memberJpaRepository.totalCount(age);
    
    //then
    assertThat(members.size()).isEqualTo(3);
    assertThat(totalCount).isEqualTo(5);
  }
  
  @Test
  public void bulkUpdate() {
    
    //given
    memberJpaRepository.save(new Member("member1", 10));
    memberJpaRepository.save(new Member("member2", 19));
    memberJpaRepository.save(new Member("member3", 20));
    memberJpaRepository.save(new Member("member4", 21));
    memberJpaRepository.save(new Member("member5", 40));
    
    //when
    int resultCount = memberJpaRepository.bulkAgePlus(20);
    
    //then
    assertThat(resultCount).isEqualTo(3);
  }
}
