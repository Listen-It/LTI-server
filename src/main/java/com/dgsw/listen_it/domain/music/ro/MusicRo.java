package com.dgsw.listen_it.domain.music.ro;

import com.dgsw.listen_it.domain.music.entity.Music;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MusicRo {

    private final long id;

    private final String artist;

    private final String title;

    private final String filename;

    @JsonProperty("album_filename")
    private final String albumFilename;

    public MusicRo(Music music) {
        this.id = music.getId();
        this.artist = music.getArtist().getNickname();
        this.title = music.getTitle();
        this.filename = music.getFilename();
        this.albumFilename = music.getAlbumPicture();
    }
}
