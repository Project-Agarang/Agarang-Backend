package org.ku.agarangproject.global.exception;

import lombok.Getter;
import org.ku.agarangproject.global.model.dto.BaseResponseStatus;

@Getter
public class FileException extends BusinessException {
    public FileException(BaseResponseStatus baseResponseStatus) {
        super(baseResponseStatus);
    }
}
