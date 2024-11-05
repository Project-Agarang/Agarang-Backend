package org.ku.agarangproject.global.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseResponseStatus {

    INVALID_FILE_EXTENSION(false, HttpStatus.BAD_REQUEST, 4003, "지원하지 않는 파일확장자입니다."),
    FAIL_FILE_READ(false, HttpStatus.FORBIDDEN, 4009, "파일 업로드에 실패했습니다. 다시 시도해주세요."),
    FAIL_S3_UPLOAD(false, HttpStatus.SERVICE_UNAVAILABLE, 5003, "S3 파일 서버 업로드에 실패했습니다.");

    private final boolean isSuccess;
    @JsonIgnore
    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, HttpStatus httpStatus, int code, String message) {
        this.isSuccess = isSuccess;
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
    }