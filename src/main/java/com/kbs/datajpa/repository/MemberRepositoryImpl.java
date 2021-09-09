package com.kbs.datajpa.repository;

import java.util.List;
import javax.persistence.EntityManager;
import com.kbs.datajpa.entity.Member;
import lombok.RequiredArgsConstructor;

//사용자 정의 인터페이스 구현 클래스 : naming = [리포지토리 인터페이스 이름 + Impl]
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

  private final EntityManager em;

  @Override
  public List<Member> findMemberCustom() {
    return em.createQuery("select m from Member m", Member.class)
             .getResultList();
  }

}
