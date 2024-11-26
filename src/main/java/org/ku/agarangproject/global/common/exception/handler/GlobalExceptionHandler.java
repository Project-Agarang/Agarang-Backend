package org.ku.agarangproject.global.common.exception.handler;

import org.ku.agarangproject.global.common.exception.exception.BusinessException;
import org.ku.agarangproject.global.common.model.dto.BaseResponse;
import org.ku.agarangproject.global.common.model.dto.BaseResponseStatus;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(0)
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<BaseResponse<BaseResponseStatus>> handleBusinessException(BusinessException e) {
    return ResponseEntity.status(e.getBaseResponseStatus().getHttpStatus())
      .body(new BaseResponse<>(e.getBaseResponseStatus()));
  }
}
