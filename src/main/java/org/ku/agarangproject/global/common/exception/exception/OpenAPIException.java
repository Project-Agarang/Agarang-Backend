package org.ku.agarangproject.global.common.exception.exception;

import lombok.Getter;
import org.ku.agarangproject.global.common.model.dto.BaseResponseStatus;

@Getter
public class OpenAPIException extends BusinessException {
  public OpenAPIException(BaseResponseStatus baseResponseStatus) {
    super(baseResponseStatus);
  }
}
