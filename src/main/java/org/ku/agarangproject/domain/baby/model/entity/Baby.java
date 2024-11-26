package org.ku.agarangproject.domain.baby.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;
import lombok.*;
import org.ku.agarangproject.domain.member.model.entity.Member;
import org.ku.agarangproject.global.common.model.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Baby extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;

    @OneToMany(mappedBy = "baby", fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String code;

    @Setter
    private String name;
    private LocalDate dueDate;
    @Setter
    private Double weight;

    @Builder
    public Baby(String code, String name, LocalDate dueDate, Double weight) {
        this.code = code;
        this.name = name;
        this.dueDate = dueDate;
        this.weight = weight;
    }

    public Baby(Long id, String code, String name, LocalDate dueDate, Double weight) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dueDate = dueDate;
        this.weight = weight;
    }
}
