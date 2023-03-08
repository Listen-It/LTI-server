package com.dgsw.listen_it.global.error.handler;

import com.dgsw.listen_it.global.error.GlobalException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorRo> undefinedException(Exception e) {
        ErrorRo errorRo = new ErrorRo(LocalDateTime.now(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorRo);
    }

    @ExceptionHandler(GlobalException.class)
    protected ResponseEntity<ErrorRo> undefinedException(GlobalException e) {
        ErrorRo errorRo = new ErrorRo(e.getOccurredAt(), e.getMessage());
        return ResponseEntity.status(e.getStatus())
                .body(errorRo);
    }

    @Getter
    @RequiredArgsConstructor
    public static class ErrorRo {
        private final LocalDateTime occurredAt;
        private final String message;
    }
}
