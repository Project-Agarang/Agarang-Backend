package org.ku.agarangproject.domain.baby.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ku.agarangproject.global.common.model.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`character`") // character : 예약어
public class Character extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String imageUrl;
    private String secondImageUrl;
}
