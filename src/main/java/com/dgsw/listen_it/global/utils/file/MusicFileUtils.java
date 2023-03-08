package com.dgsw.listen_it.global.utils.file;

import com.dgsw.listen_it.global.utils.LocalFileUtils;
import org.springframework.util.ObjectUtils;

public class MusicFileUtils extends LocalFileUtils {

    @Override
    protected String getDatePattern() {
        return "yyyyMMdd";
    }

    @Override
    public String getPath() {
        return "sounds/";
    }

    @Override
    protected String getFileExtension(String contentType) {
        if (ObjectUtils.isEmpty(contentType)) {
            throw new IllegalArgumentException();
        }

        if (contentType.contains("audio/mpeg")) {
            return ".mp3";
        }
        else if (contentType.contains("audio/ogg")) {
            return ".ogg";
        }
        else if (contentType.contains("audio/vnd.rn-realaudio")) {
            return ".ra";
        }
        else {
            throw new IllegalArgumentException();
        }
    }

}
