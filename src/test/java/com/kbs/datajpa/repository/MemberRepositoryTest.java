package com.kbs.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import com.kbs.datajpa.dto.MemberDto;
import com.kbs.datajpa.entity.Member;
import com.kbs.datajpa.entity.Team;

@SpringBootTest
@Transactional
//@Rollback(false)
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;
  
  @Autowired
  TeamRepository teamRepository;
  
  @Test
  void testMember() {
    Member member = new Member("memberA");
    Member savedMember = memberRepository.save(member);
    
    Member findMember = memberRepository.findById(savedMember.getId()).get();
    
    assertThat(findMember.getId()).isEqualTo(member.getId());
    assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
    assertThat(findMember).isEqualTo(member);
  }

  @Test
  public void basicCRUD() {
    
    Member member1 = new Member("member1");
    Member member2 = new Member("member2");

    memberRepository.save(member1);
    memberRepository.save(member2);
    
    Member findMember1 = memberRepository.findById(member1.getId()).get();
    Member findMember2 = memberRepository.findById(member2.getId()).get();
    
    assertThat(findMember1).isEqualTo(member1);
    assertThat(findMember2).isEqualTo(member2);
    
    List<Member> all = memberRepository.findAll();
    assertThat(all.size()).isEqualTo(2);
    
    long count = memberRepository.count();
    assertThat(count).isEqualTo(2);
    
    memberRepository.delete(member1);
    memberRepository.delete(member2);
    
    long deletedCount = memberRepository.count();
    assertThat(deletedCount).isEqualTo(0);
    
  }
  
  @Test
  public void findByUserNameAndAgeGreaterThan() {
    
    Member m1 = new Member("AAA",10);
    Member m2 = new Member("AAA",20);
    
    memberRepository.save(m1);
    memberRepository.save(m2);
    
    List<Member> result = memberRepository.findByUserNameAndAgeGreaterThan("AAA", 15);
    
    assertThat(result.get(0).getUserName()).isEqualTo("AAA");
    assertThat(result.get(0).getAge()).isEqualTo(20);
    assertThat(result.size()).isEqualTo(1);
    
  }
  
  @Test
  public void testNamedQuery() {
    Member m1 = new Member("AAA",10);
    Member m2 = new Member("BBB",20);
    
    memberRepository.save(m1);
    memberRepository.save(m2);
    
    List<Member> result = memberRepository.findByUserName("AAA");
    
    assertThat(result.get(0).getUserName()).isEqualTo("AAA");
  }
  
  @Test
  public void testQuery() {
    Member m1 = new Member("AAA",10);
    Member m2 = new Member("BBB",20);
    
    memberRepository.save(m1);
    memberRepository.save(m2);
    
    List<Member> result = memberRepository.findUser("AAA",10);
    
    assertThat(result.get(0)).isEqualTo(m1);
  }
  
  @Test
  public void findUserNameList() {
    Member m1 = new Member("AAA",10);
    Member m2 = new Member("BBB",20);
    
    memberRepository.save(m1);
    memberRepository.save(m2);
    
    List<String> result = memberRepository.findUserNameList();
    
    for(String s : result) {
      System.out.println("s = "+s);
    }
  }
  
  @Test
  public void findMemberDto() {
    
    Team team = new Team("teamA");
    teamRepository.save(team);
    
    Member m1 = new Member("AAA",10,team);
    Member m2 = new Member("BBB",20,team);
    
    memberRepository.save(m1);
    memberRepository.save(m2);
    
    List<MemberDto> result = memberRepository.findMemberDto();
    
    for(MemberDto dto : result) {
      System.out.println("[[[dto = " + dto);
    }
  }
  
  @Test
  public void findByNames() {
    Member m1 = new Member("AAA",10);
    Member m2 = new Member("BBB",20);
    
    memberRepository.save(m1);
    memberRepository.save(m2);
    
    List<Member> result = memberRepository.findByNames(Arrays.asList("AAA","BBB"));
    
    for(Member member : result) {
      System.out.println("[[[member = "+member);
    }
  }
  
  @Test
  public void testFindReturnType() {
    Member m1 = new Member("AAA",10);
    Member m2 = new Member("BBB",20);
    
    memberRepository.save(m1);
    memberRepository.save(m2);
    
    List<Member> findListByUserName = memberRepository.findListByUserName("AAA");
    Member findOneByUserName = memberRepository.findOneByUserName("AAA");
    Optional<Member> findOptionalByUserName = memberRepository.findOptionalByUserName("AAA");
    
    for(Member member : findListByUserName) {
      System.out.println("[[[ findListByUserName : member = "+member);
    }
    
    System.out.println("[[[ findOneByUserName : " + findOneByUserName);
    
    System.out.println("[[[ findOptionalByUserName : " + findOptionalByUserName);
  }
  
  @Test
  public void page() {

    // given
    memberRepository.save(new Member("member1", 10));
    memberRepository.save(new Member("member2", 10));
    memberRepository.save(new Member("member3", 10));
    memberRepository.save(new Member("member4", 10));
    memberRepository.save(new Member("member5", 10));
    
    // when
    PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "userName"));
    
    Page<Member> page = memberRepository.findByAge(10, pageRequest);
    
    // then
    List<Member> content = page.getContent(); // 조회된 데이터
    assertThat(content.size()).isEqualTo(3); // 조회된 데이터 수
    assertThat(page.getTotalElements()).isEqualTo(5); // 전체 데이터 수
    assertThat(page.getNumber()).isEqualTo(0); // 페이지 번호
    assertThat(page.getTotalPages()).isEqualTo(2); // 전체 페이지 번호
    assertThat(page.isFirst()).isTrue(); // 첫번째 항목인가?
    assertThat(page.hasNext()).isTrue(); // 다음 페이지가 있는가?
  }
  
  @Test
  public void page2() {

    memberRepository.save(new Member("member1", 10));
    memberRepository.save(new Member("member2", 10));
    memberRepository.save(new Member("member3", 10));
    memberRepository.save(new Member("member4", 10));
    memberRepository.save(new Member("member5", 10));
    
    PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "userName"));
    
    Page<Member> page = memberRepository.findPageByUserName("member1", pageRequest);
    
    System.out.println("[[[page2:"+page.getContent().size());
    System.out.println("[[[page2:"+page.getContent().get(0));
  }
}
