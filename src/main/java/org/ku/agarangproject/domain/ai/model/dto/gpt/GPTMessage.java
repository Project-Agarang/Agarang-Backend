package org.ku.agarangproject.domain.ai.model.dto.gpt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ku.agarangproject.domain.ai.model.enums.GPTRole;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GPTMessage {
    private String role;
    private Object content;

    @Builder
    public GPTMessage(GPTRole role, Object content) {
        this.role = role.toLowerString();
        this.content = content;
    }
}
