package com.kbs.datajpa.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import com.kbs.datajpa.dto.MemberDto;
import com.kbs.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{
  
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

  // 단건
  Member findOneByUserName(String name);

  // 단건 Optional
  Optional<Member> findOptionalByUserName(String name);


  
  // 페이정과 정렬
  Page<Member> findPageByUserName(String name, Pageable pageable); // count 쿼리 사용

  Slice<Member> findSliceByUserName(String name, Pageable pageable); // count 쿼리 미사용 : 내부적으로 limit+1 조회 (조회 후 더보기 로 활용)

  List<Member> findPageListByUserName(String name, Pageable pageable); // count 쿼리 미사용

  List<Member> findSortByUserName(String name, Sort sort);

  Page<Member> findByAge(int age, Pageable pageable);



  // 벌크성 수정 쿼리
  @Modifying // @Modifying 수정,삭제 쿼리에 필수
  // @Modifying(clearAutomatically = true) // 쿼리 실행 후 영속성 컨텍스트 자동 초기화
  @Query("update Member m set m.age = m.age+1 where m.age >= :age")
  int bulkAgePlus(@Param("age") int age);



  // N+1 문제 해결
  // JPQL 페치 조인
  @Query("select m from Member m left join fetch m.team")
  List<Member> findMemberFetchJoin();

  // EntityGraph
  @Override
  @EntityGraph(attributePaths = {"team"})
  List<Member> findAll();

  // JPQL + 엔티티 그래프
  @Query("select m from Member m")
  @EntityGraph(attributePaths = {"team"})
  List<Member> findMemberEntityGraph();

  // 메서드 이름 쿼리
  @EntityGraph(attributePaths = {"team"})
  List<Member> findEntityGraphByAge(int age);



  // 쿼리 힌트 사용
  // 조회용도로만 사용, 데티체크 미발생
  @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
  Member findReadOnlyByUserName(String userName);
  
  // Lock : 조회 블럭에 Lock을 검 (for update)
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  List<Member> findHintLockByUserName(String name);
}
