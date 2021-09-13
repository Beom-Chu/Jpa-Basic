package com.kbs.datajpa;

import java.util.List;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.kbs.datajpa.entity.Member;
import com.kbs.datajpa.entity.Team;
import com.kbs.datajpa.repository.MemberRepository;
import com.kbs.datajpa.repository.UserNameOnly;

@SpringBootTest
@Transactional
class ProjectionsTest {

  @Autowired
  private EntityManager em;

  @Autowired
  private MemberRepository memberRepository;

  @Test
  void projections() {

    // given
    Team teamA = new Team("teamA");
    em.persist(teamA);

    Member m1 = new Member("m1", 0, teamA);
    Member m2 = new Member("m2", 0, teamA);
    em.persist(m1);
    em.persist(m2);
    em.flush();
    em.clear();

    // when
    List<UserNameOnly> result = memberRepository.findProjectionsByUserName("m1");
    // SQL에서도 select절에서 username만 조회(Projection)하는 것을 확인

    // then
    Assertions.assertThat(result.size()).isEqualTo(1);

    for (UserNameOnly s : result) {
      System.out.println("[[[getUserName : " + s.getUserName());
    }
  }

}
