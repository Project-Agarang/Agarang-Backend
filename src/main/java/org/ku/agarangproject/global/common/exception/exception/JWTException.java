package org.ku.agarangproject.global.common.exception.exception;

import lombok.Getter;
import org.ku.agarangproject.global.common.model.dto.BaseResponseStatus;

@Getter
public class JWTException extends BusinessException {
  public JWTException(BaseResponseStatus baseResponseStatus) {
    super(baseResponseStatus);
  }
}
