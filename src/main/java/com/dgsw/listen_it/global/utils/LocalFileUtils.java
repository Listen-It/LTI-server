package com.dgsw.listen_it.global.utils;

import com.dgsw.listen_it.global.utils.file.AlbumFileUtils;
import com.dgsw.listen_it.global.utils.file.MusicFileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class LocalFileUtils {

    public String saveFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(getDatePattern());
        String currentDate = dateFormat.format(new Date());

        String absolutePath = new File("").getAbsolutePath()
                + "\\";
        String path = getPath() + currentDate;
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new IllegalStateException();
            }
        }

        String contentType = multipartFile.getContentType();
        String originalFileExtension = getFileExtension(contentType);
        String newFilename = System.nanoTime() + originalFileExtension;
        String pathname = path + "/" + newFilename;

        file = new File(absolutePath + pathname);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pathname;
    }
    
    protected abstract String getDatePattern();

    public abstract String getPath();

    protected abstract String getFileExtension(String contentType);

    public enum Type {
        MUSIC, ALBUM
    }

    public static LocalFileUtils getInstance(Type type) {
        return switch (type) {
            case MUSIC -> new MusicFileUtils();
            case ALBUM -> new AlbumFileUtils();
        };
    }

}
