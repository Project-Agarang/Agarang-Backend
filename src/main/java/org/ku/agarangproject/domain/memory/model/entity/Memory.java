package org.ku.agarangproject.domain.memory.model.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.ku.agarangproject.domain.baby.model.entity.Baby;
import org.ku.agarangproject.domain.member.model.entity.Member;
import org.ku.agarangproject.domain.memory.enums.Genre;
import org.ku.agarangproject.domain.memory.enums.Instrument;
import org.ku.agarangproject.domain.memory.enums.Mood;
import org.ku.agarangproject.domain.memory.enums.Tempo;
import org.ku.agarangproject.global.common.model.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baby_id")
    private Baby baby;

    private String imageUrl;
    private String musicTitle;
    private String text;

    @Setter
    private String musicGenId;
    @Setter
    private String musicUrl;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    private Mood mood;

    @Enumerated(EnumType.STRING)
    private Tempo tempo;

    @Enumerated(EnumType.STRING)
    private Instrument instrument;

    @Setter
    @OneToMany(mappedBy = "memory", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 3)
    private List<Hashtag> hashtags;

    @Builder
    public Memory(Long id, Member member, Baby baby, String imageUrl, String musicTitle, String musicUrl, String text, String musicGenId, Genre genre, Mood mood, Tempo tempo, Instrument instrument, List<Hashtag> hashtags) {
        this.id = id;
        this.member = member;
        this.baby = baby;
        this.imageUrl = imageUrl;
        this.musicTitle = musicTitle;
        this.musicUrl = musicUrl;
        this.text = text;
        this.musicGenId = musicGenId;
        this.genre = genre;
        this.mood = mood;
        this.tempo = tempo;
        this.instrument = instrument;
        this.hashtags = hashtags;
    }

    public void updateMemory(String text) {
        this.text = text;
    }
}
