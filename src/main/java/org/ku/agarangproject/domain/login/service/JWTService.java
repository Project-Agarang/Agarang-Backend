package org.ku.agarangproject.domain.login.service;

import static org.ku.agarangproject.global.common.model.dto.BaseResponseStatus.EXPIRED_REFRESH_TOKEN;
import static org.ku.agarangproject.global.common.model.dto.BaseResponseStatus.INVALID_REFRESH_TOKEN;
import static org.ku.agarangproject.global.common.model.dto.BaseResponseStatus.NOT_FOUND_MEMBER;
import static org.ku.agarangproject.global.common.model.dto.BaseResponseStatus.NOT_FOUND_REFRESH_TOKEN;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ku.agarangproject.domain.login.model.dto.ReissueDto;
import org.ku.agarangproject.domain.login.utils.JWTUtil;
import org.ku.agarangproject.domain.member.model.entity.Member;
import org.ku.agarangproject.domain.member.repository.MemberRepository;
import org.ku.agarangproject.global.common.exception.exception.JWTException;
import org.ku.agarangproject.global.common.service.RedisService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class JWTService {

  private final JWTUtil jwtUtil;
  private final RedisService redisService;
  private final MemberRepository memberRepository;

  public ReissueDto reissueTokens(String oldRefresh) {

    // RefreshToken 유효성 검증
    Member member = validateRefreshToken(oldRefresh);

    // AccessToken 생성 및 Refresh Rotate
    String newAccess = jwtUtil.createAccessToken(member.getProviderId(), member.getRole(), member.getId());
    String newRefresh = jwtUtil.createRefreshToken(member.getProviderId(), member.getRole(), member.getId());

    // Refresh Token Update
    redisService.save(member.getProviderId(), newRefresh);

    return ReissueDto.builder()
      .newAccessToken(newAccess)
      .newRefreshToken(newRefresh)
      .providerId(member.getProviderId())
      .role(member.getRole())
      .build();
  }

  private Member validateRefreshToken(String oldRefresh) {
    if (oldRefresh == null) {
      log.info("reissue : refresh token is null (cookie null)");
      throw new JWTException(NOT_FOUND_REFRESH_TOKEN);
    }

    try {
      jwtUtil.isExpired(oldRefresh);
    } catch (ExpiredJwtException e) {
      throw new JWTException(EXPIRED_REFRESH_TOKEN);
    }

    String providerId = jwtUtil.getProviderId(oldRefresh);
    String savedRefresh = redisService.get(providerId, String.class)
      .orElseThrow(() -> new JWTException(NOT_FOUND_REFRESH_TOKEN));

    if (!oldRefresh.equals(savedRefresh)) {
      throw new JWTException(INVALID_REFRESH_TOKEN);
    }


    String category = jwtUtil.getCategory(oldRefresh);
    if (!category.equals("refresh")) {
      throw new JWTException(INVALID_REFRESH_TOKEN);
    }

    return memberRepository.findById(jwtUtil.getMemberId(oldRefresh))
      .orElseThrow(() -> new JWTException(NOT_FOUND_MEMBER));
  }
}
