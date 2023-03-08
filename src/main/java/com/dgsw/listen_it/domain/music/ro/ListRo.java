package com.dgsw.listen_it.domain.music.ro;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ListRo<T> {

    private final List<T> content;

}
