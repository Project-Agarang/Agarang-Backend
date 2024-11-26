package org.ku.agarangproject.domain.login.controller;

import static org.ku.agarangproject.global.common.model.dto.BaseResponseStatus.AUTHORIZATION_SUCCESS;
import static org.ku.agarangproject.global.common.model.dto.BaseResponseStatus.SUCCESS;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ku.agarangproject.domain.login.model.dto.CustomOAuth2User;
import org.ku.agarangproject.domain.member.model.dto.BabyCodeRequest;
import org.ku.agarangproject.domain.member.model.dto.ProcessBabyRequest;
import org.ku.agarangproject.domain.member.service.MemberService;
import org.ku.agarangproject.global.common.model.dto.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

  private final MemberService memberService;

  @PostMapping("/baby-code")
  public ResponseEntity<BaseResponse<Void>> verifyBabyCode(@AuthenticationPrincipal CustomOAuth2User details,
                                                           @RequestBody BabyCodeRequest request) {
    memberService.verifyBabyCode(details.getMemberId(), request.getBabyCode());
    return ResponseEntity.ok(new BaseResponse<>(SUCCESS));
  }

  @PostMapping("/process-baby")
  public ResponseEntity<BaseResponse<Void>> processBabyAssignment(@AuthenticationPrincipal CustomOAuth2User details,
                                                                  @RequestBody ProcessBabyRequest request) {
    memberService.processBabyAssignment(details.getMemberId(), request);
    return ResponseEntity.ok(new BaseResponse<>(SUCCESS));
  }

  @GetMapping("/success")
  public ResponseEntity<BaseResponse<Void>> loginSuccess() {
    return ResponseEntity.ok(new BaseResponse<>(AUTHORIZATION_SUCCESS));
  }
}
