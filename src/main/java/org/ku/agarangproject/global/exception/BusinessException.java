package org.ku.agarangproject.global.exception;

import lombok.Getter;
import org.ku.agarangproject.global.model.dto.BaseResponseStatus;

@Getter
public class BusinessException extends RuntimeException {
    private final BaseResponseStatus baseResponseStatus;

    public BusinessException(BaseResponseStatus baseResponseStatus) {
        super(baseResponseStatus.getMessage());
        this.baseResponseStatus = baseResponseStatus;
    }
}
