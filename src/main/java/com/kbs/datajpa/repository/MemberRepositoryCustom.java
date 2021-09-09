package com.kbs.datajpa.repository;

import java.util.List;
import com.kbs.datajpa.entity.Member;

//사용자 정의 인터페이스
public interface MemberRepositoryCustom {
  List<Member> findMemberCustom();
}
