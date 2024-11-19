package org.ku.agarangproject.domain.login.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ku.agarangproject.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final MemberRepository memberRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    OAuth2User oAuth2User = super.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    OAuth2Response oAuth2Response;
    log.info("oAuth2User.getAttributes() = {}", oAuth2User.getAttributes());
    switch (registrationId) {
      case "naver" -> oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
      case "kakao" -> oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
      case "google" -> oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
      default -> {
        return null;
      }
    }

    String providerId = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();
    Optional<Member> optionalMember = memberRepository.findByProviderId(providerId);

    Member member;
    if (optionalMember.isEmpty()) {
      member = Member.of(providerId, oAuth2Response.getName(), oAuth2Response.getEmail(), "ROLE_USER");
      memberRepository.save(member);
    } else {
      member = optionalMember.get();
      member.changeInfo(oAuth2Response.getName(), oAuth2Response.getEmail());
    }
    return new CustomOAuth2User(MemberDTO.from(member));
  }
}
