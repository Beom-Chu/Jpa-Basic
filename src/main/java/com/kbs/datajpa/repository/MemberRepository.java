package com.kbs.datajpa.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.kbs.datajpa.dto.MemberDto;
import com.kbs.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
  
  List<Member> findByUserNameAndAgeGreaterThan(String userName, int age);
  
  @Query(name = "Member.findByUserName")
  List<Member> findByUserName(@Param("userName") String userName);
  
  @Query("select m from Member m where m.userName = :userName and m.age = :age")
  List<Member> findUser(@Param("userName") String userName, @Param("age") int age);
  
  @Query("select m.userName from Member m")
  List<String> findUserNameList();
  
  @Query("select new com.kbs.datajpa.dto.MemberDto(m.id, m.userName, t.name) from Member m join m.team t")
  List<MemberDto> findMemberDto();
  
  @Query("select m from Member m where m.userName in :names")
  List<Member> findByNames(@Param("names") List<String> names);
  
  
  // 반환 타입별 조회
  // 컬렉션
  List<Member> findListByUserName(String name);
  //단건
  Member findOneByUserName(String name);
  //단건 Optional
  Optional<Member> findOptionalByUserName(String name);
  
  
  //페이정과 정렬
  Page<Member> findPageByUserName(String name, Pageable pageable);  //count 쿼리 사용
  Slice<Member> findSliceByUserName(String name, Pageable pageable);  //count 쿼리 미사용 : 내부적으로 limit+1 조회 (조회 후 더보기 로 활용)
  List<Member> findPageListByUserName(String name, Pageable pageable);  //count 쿼리 미사용
  List<Member> findSortByUserName(String name, Sort sort);
  
  Page<Member> findByAge(int age, Pageable pageable);
}
