package com.kbs.datajpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "userName", "age"})
public class Member {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;
  private String userName;
  private int age;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private Team team;

  public Member(String userName) {
    this(userName, 0);
  }

  public Member(String userName, int age) {
    this(userName, age, null);
  }

  public Member(String userName, int age, Team team) {
    this.userName = userName;
    this.age = age;
    if (team != null) {
      changeTeam(team);
    }
  }

  /* 연관관계 편의 메소드 */
  private void changeTeam(Team team) {

    if (this.team != null) {
      this.team.getMembers().remove(this);
    }

    this.team = team;
    team.getMembers().add(this);
  }


}
