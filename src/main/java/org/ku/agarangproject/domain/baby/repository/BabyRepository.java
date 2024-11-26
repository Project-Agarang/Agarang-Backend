package org.ku.agarangproject.domain.baby.repository;

import org.ku.agarangproject.domain.baby.model.entity.Baby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BabyRepository extends JpaRepository<Baby, Long> {
  Optional<Baby> findByCode(String code);

  @Query("SELECT b FROM Baby b JOIN b.members m WHERE m.id = :memberId")
  Optional<Baby> findByMemberId(Long memberId);
}

