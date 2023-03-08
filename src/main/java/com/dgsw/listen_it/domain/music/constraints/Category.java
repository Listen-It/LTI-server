package com.dgsw.listen_it.domain.music.constraints;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    LATEST("id", true),
    LIKES("", false)
    ;

    private final String topic;
    private final boolean ascending;
}
