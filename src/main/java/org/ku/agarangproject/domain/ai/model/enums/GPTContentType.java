package org.ku.agarangproject.domain.ai.model.enums;

import lombok.Getter;

@Getter
public enum GPTContentType {
    TEXT, IMAGE_URL;

    public String toLowerString() {
        return this.toString().toLowerCase();
    }
}
