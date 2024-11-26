package org.ku.agarangproject.domain.baby.repository;

import org.ku.agarangproject.domain.baby.model.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {

}
