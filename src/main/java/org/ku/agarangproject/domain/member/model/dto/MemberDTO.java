package org.ku.agarangproject.domain.member.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ku.agarangproject.domain.member.model.entity.Member;

@Getter
@NoArgsConstructor
public class MemberDTO {

  private Long memberId;
  private String providerId;
  private String name;
  private String role;

  @Builder
  public MemberDTO(Long memberId, String providerId, String name, String role) {
    this.memberId = memberId;
    this.providerId = providerId;
    this.name = name;
    this.role = role;
  }

  public static MemberDTO from(Member member) {
    return MemberDTO.builder()
      .memberId(member.getId())
      .providerId(member.getProviderId())
      .name(member.getName())
      .role(member.getRole())
      .build();
  }
}
