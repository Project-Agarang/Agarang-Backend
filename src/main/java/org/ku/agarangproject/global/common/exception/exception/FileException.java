package org.ku.agarangproject.global.common.exception.exception;

import lombok.Getter;
import org.ku.agarangproject.global.common.model.dto.BaseResponseStatus;

@Getter
public class FileException extends BusinessException {
  public FileException(BaseResponseStatus baseResponseStatus) {
    super(baseResponseStatus);
  }
}
