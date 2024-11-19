package org.ku.agarangproject.domain.playlist.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ku.agarangproject.domain.memory.model.entity.Memory;
import org.ku.agarangproject.global.common.model.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemoryPlaylist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memory_id")
    private Memory memory;

    public MemoryPlaylist(Memory memory, Playlist playlist) {
        this.playlist = playlist;
        this.memory = memory;
    }
}
