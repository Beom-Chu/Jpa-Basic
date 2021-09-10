package com.kbs.datajpa.controller;

import javax.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.kbs.datajpa.dto.MemberDto;
import com.kbs.datajpa.entity.Member;
import com.kbs.datajpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberRepository memberRepository;

  @PostConstruct
  public void init() {
    for(int i=0; i<20; i++) {
      memberRepository.save(new Member("member"+i, i));
    }
  }
  
  
  /* ▽▽▽▽도메인 컨버터▽▽▽▽ */

  /* HTTP 요청은 회원 id 를 받지만 도메인 클래스 컨버터가 중간에 동작해서 회원 엔티티 객체를 반환 */
  /* 도메인 클래스 컨버터도 리파지토리를 사용해서 엔티티를 찾음 */
  /* 주의: 도메인 클래스 컨버터로 엔티티를 파라미터로 받으면, 이 엔티티는 단순 조회용으로만 사용해야 한다. */

  // 도메인 클래스 컨버터 사용 전
  @GetMapping("/member/{id}")
  public String findMember(@PathVariable("id") Long id) {
    Member member = memberRepository.findById(id).get();
    return member.getUserName();
  }

  // 도메인 클래스 컨버터 사용 후
  @GetMapping("/member2/{id}")
  public String findMember2(@PathVariable("id") Member member) {
    return member.getUserName();
  }

  /* △△△△도메인 컨버터△△△△ */
  
  
  
  
  /* ▽▽▽▽페이징과 정렬▽▽▽▽ */

  /* 파라미터로 Pageable 을 받을 수 있다. */
  /* Pageable 은 인터페이스, 실제는 org.springframework.data.domain.PageRequest 객체 생성 */
  /* ex) /members?page=0&size=3&sort=id,desc&sort=username,desc */

  @GetMapping("/members")
  public Page<Member> List(Pageable pageable) {
    return memberRepository.findAll(pageable);
  }
  
  //page default 설정
  @GetMapping("/members2")
  public Page<Member> List2(@PageableDefault(size = 5, sort = "userName", direction = Sort.Direction.DESC) Pageable pageable) {
    return memberRepository.findAll(pageable);
  }
  
  //Page 내용을 DTO로 변환
  @GetMapping("/members3")
  public Page<MemberDto> List3(Pageable pageable) {
    return memberRepository.findAll(pageable).map(MemberDto::new);
  }

  /* △△△△페이징과 정렬△△△△ */
}
