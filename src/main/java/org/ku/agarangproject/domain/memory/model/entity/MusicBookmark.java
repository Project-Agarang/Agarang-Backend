package org.ku.agarangproject.domain.memory.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ku.agarangproject.domain.member.model.entity.Member;
import org.ku.agarangproject.global.common.model.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicBookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memory_id")
    private Memory memory;

    public MusicBookmark(Member member, Memory memory) {
        this.member = member;
        this.memory = memory;
    }
}
