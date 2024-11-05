package org.ku.agarangproject.global.model.entity;

import static org.ku.agarangproject.global.model.enums.Status.ACTIVE;
import static org.ku.agarangproject.global.model.enums.Status.INACTIVE;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.ku.agarangproject.global.model.enums.Status;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public abstract class BaseEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = ACTIVE;

    public void changeStatusToInActive() {
        this.status = INACTIVE;
    }
}