package com.dgsw.listen_it.global.convert;

import com.dgsw.listen_it.domain.music.constraints.Category;
import org.springframework.core.convert.converter.Converter;

public class CategoryRequestConverter implements Converter<String, Category> {

    @Override
    public Category convert(String source) {
        return Category.valueOf(source.toUpperCase());
    }
}
