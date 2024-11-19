package org.ku.agarangproject.domain.login.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.ku.agarangproject.domain.login.model.dto.ReissueDto;
import org.ku.agarangproject.domain.login.service.JWTService;
import org.ku.agarangproject.domain.login.utils.CookieUtil;
import org.ku.agarangproject.global.common.model.dto.BaseResponse;
import org.ku.agarangproject.global.common.model.dto.BaseResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReissueController {

  private final JWTService jwtService;
  private final CookieUtil cookieUtil;

  @PostMapping("/reissue")
  public ResponseEntity<BaseResponse<Void>> reissue(
    @CookieValue(value = "REFRESH", required = false) String refresh, HttpServletResponse response) {

    ReissueDto reissueDto = jwtService.reissueTokens(refresh);

    response.addCookie(cookieUtil.createCookie("ACCESS", reissueDto.getNewAccessToken()));
    response.addCookie(cookieUtil.createCookie("REFRESH", reissueDto.getNewRefreshToken()));
    return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.AUTHORIZATION_SUCCESS));
  }
}