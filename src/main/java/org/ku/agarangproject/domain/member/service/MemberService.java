package org.ku.agarangproject.domain.member.service;

import static org.ku.agarangproject.global.common.model.dto.BaseResponseStatus.NOT_FOUND_BABY;
import static org.ku.agarangproject.global.common.model.dto.BaseResponseStatus.NOT_FOUND_CHARACTER;
import static org.ku.agarangproject.global.common.model.dto.BaseResponseStatus.NOT_FOUND_MEMBER;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ku.agarangproject.domain.baby.model.entity.Baby;
import org.ku.agarangproject.domain.baby.repository.BabyRepository;
import org.ku.agarangproject.domain.baby.repository.CharacterRepository;
import org.ku.agarangproject.domain.member.model.dto.ProcessBabyRequest;
import org.ku.agarangproject.domain.member.model.entity.Member;
import org.ku.agarangproject.domain.member.repository.MemberRepository;
import org.ku.agarangproject.global.common.exception.exception.BusinessException;
import org.ku.agarangproject.global.common.utils.CodeUtil;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

  private final MemberRepository memberRepository;
  private final BabyRepository babyRepository;
  private final CharacterRepository characterRepository;

  public void verifyBabyCode(Long id, String babyCode) {

    Baby baby = babyRepository.findByCode(babyCode)
        .orElseThrow(() -> new BusinessException(NOT_FOUND_BABY));

    Member member = memberRepository.findById(id)
        .orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBER));

    member.addBaby(baby);
  }

  public void processBabyAssignment(Long id, ProcessBabyRequest request) {

    Member member = memberRepository.findById(id)
        .orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBER));

    if (member.getBaby() == null) {

      Baby baby = Baby.builder()
          .name(request.getBabyName())
          .dueDate(request.getDueDate())
          .weight(1.4) // Default Value
          .code(CodeUtil.generateUniqueCode())
          .build();

      // Default Value
      baby.setCharacter(characterRepository.findById(6L)
          .orElseThrow(() -> new BusinessException(NOT_FOUND_CHARACTER)));

      babyRepository.save(baby);
      member.addBaby(baby);
    }

    member.setFamilyRole(request.getFamilyRole());
  }
}
