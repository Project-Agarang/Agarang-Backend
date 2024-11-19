package org.ku.agarangproject.domain.memory.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ku.agarangproject.global.common.model.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memory_id")
    private Memory memory;

    private String name; // 해시태그 명

    @Builder
    public Hashtag(Long id, Memory memory, String name) {
        this.id = id;
        this.memory = memory;
        this.name = name;
    }
}
