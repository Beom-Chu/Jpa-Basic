package com.kbs.datajpa.controller;

import javax.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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
  
  
  /*▽▽▽▽도메인 컨버터▽▽▽▽*/
  
  /*HTTP 요청은 회원 id 를 받지만 도메인 클래스 컨버터가 중간에 동작해서 회원 엔티티 객체를 반환*/
  /*도메인 클래스 컨버터도 리파지토리를 사용해서 엔티티를 찾음*/
  /*주의: 도메인 클래스 컨버터로 엔티티를 파라미터로 받으면, 이 엔티티는 단순 조회용으로만 사용해야 한다. */
  
  //도메인 클래스 컨버터 사용 전
  @GetMapping("/member/{id}")
  public String findMember(@PathVariable("id") Long id) {
    Member member = memberRepository.findById(id).get();
    return member.getUserName();
  }
  
  //도메인 클래스 컨버터 사용 후
  @GetMapping("/member2/{id}")
  public String findMember2(@PathVariable("id") Member member) {
    return member.getUserName();
  }
  
  /*△△△△도메인 컨버터△△△*/
  
  
}
