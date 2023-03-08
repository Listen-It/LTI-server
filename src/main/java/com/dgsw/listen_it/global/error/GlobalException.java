package com.dgsw.listen_it.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public abstract class GlobalException extends RuntimeException {

    private static final Long serialVersionUID = 156472438938249243L;

    private final LocalDateTime occurredAt = LocalDateTime.now();
    private final HttpStatus status;
    private final String message;

}
