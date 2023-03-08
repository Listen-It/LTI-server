package com.dgsw.listen_it.global.utils.file;

import com.dgsw.listen_it.global.utils.LocalFileUtils;
import org.springframework.util.ObjectUtils;

public class AlbumFileUtils extends LocalFileUtils {

    @Override
    protected String getDatePattern() {
        return "yyyyMMdd";
    }

    @Override
    public String getPath() {
        return "albums/";
    }

    @Override
    protected String getFileExtension(String contentType) {
        if (ObjectUtils.isEmpty(contentType)) {
            throw new IllegalArgumentException();
        }

        if (contentType.contains("image/jpeg")) {
            return ".jpg";
        }
        else if (contentType.contains("image/png")) {
            return ".png";
        }
        else if (contentType.contains("image/gif")) {
            return ".gif";
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
