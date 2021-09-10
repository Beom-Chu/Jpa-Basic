package com.kbs.datajpa.dto;

import com.kbs.datajpa.entity.Member;
import lombok.Data;

@Data
public class MemberDto {

  private Long id;
  private String userName;
  private String teamName;

  public MemberDto() {}

  public MemberDto(Long id, String userName, String teamName) {
    this.id = id;
    this.userName = userName;
    this.teamName = teamName;
  }

  public MemberDto(Member member) {
    this.id = member.getId();
    this.userName = member.getUserName();
    this.teamName = member.getTeam() == null ? "" : member.getTeam().getName();
  }
}
