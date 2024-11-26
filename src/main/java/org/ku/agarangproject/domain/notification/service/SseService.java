package org.ku.agarangproject.domain.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.ku.agarangproject.global.common.exception.exception.BusinessException;
import org.ku.agarangproject.global.common.model.dto.BaseResponseStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SseService {

  private final ConcurrentHashMap<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

  public SseEmitter connect(Long memberId) {

    SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

    try {
      emitter.send(SseEmitter.event()
          .name("connect")
          .data("connected!")); // 503에러 방지를 위한 더미
    } catch (IOException e) {
      emitter.completeWithError(e);
      throw new BusinessException(BaseResponseStatus.FAIL_CREATE_EMITTER);
    }

    emitter.onError(e -> {
      log.error("Error on SSE connection for memberId: {}", memberId, e);
      emitters.remove(memberId);
    });

    emitter.onCompletion(() -> {
      log.info("onCompletion callback for memberId: {}", memberId);
      emitters.remove(memberId);
    });

    emitter.onTimeout(() -> {
      log.info("onTimeout callback for memberId: {}", memberId);
      emitter.complete();
    });

    emitters.put(memberId, emitter);
    return emitter;
  }

  public void sendNotification(Long memberId, String message) {
    SseEmitter emitter = emitters.get(memberId);
    if (emitter != null) {
      try {
        emitter.send(SseEmitter.event()
            .name("notification")
            .data(message, MediaType.TEXT_PLAIN));
        log.info("memberId : {} , sendNotification", memberId);
      } catch (IOException e) {
        log.error("Error on send notification for memberId: {}", memberId, e);
        emitters.remove(memberId);
      }
    }
  }

  public void disconnect(Long memberId) {
    emitters.remove(memberId);
    log.info("memberId : {} , disconnect", memberId);
  }
}
