package com.dgsw.listen_it.domain.music.entity;

import com.dgsw.listen_it.domain.favorites.entity.Favorite;
import com.dgsw.listen_it.domain.user.entity.User;
import com.dgsw.listen_it.global.error.GlobalException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Music {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User artist;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String filename;

    @Column(name = "album_picture")
    private String albumPicture;

    @Builder.Default
    @OneToMany(mappedBy = "music")
    private List<Favorite> favorites = new ArrayList<>();

    public void modifyTitle(String title) {
        this.title = title;
    }

    public void setArtist(User artist) {
        this.artist = artist;
    }

    public void addFavorite(Favorite favorite) {
        favorites.add(favorite);
    }

    public void removeFavorite(Favorite favorite) {
        favorites.remove(favorite);
    }

    public void modifyAlbumPicture(String albumPicture) {
        this.albumPicture = albumPicture;
    }

    public static class AlreadyHasSameTitleException extends GlobalException {
        public AlreadyHasSameTitleException(String message) {
            super(HttpStatus.CONFLICT, message);
        }

        public AlreadyHasSameTitleException() {
            super(HttpStatus.CONFLICT, "이미 동일한 제목의 음원을 등록했습니다.");
        }
    }

    public static class MusicNotFoundException extends GlobalException {
        public MusicNotFoundException(String message) {
            super(HttpStatus.NOT_FOUND, message);
        }

        public MusicNotFoundException() {
            super(HttpStatus.NOT_FOUND, "음원을 찾지 못했습니다.");
        }
    }

    public static class ArtistIsNotSameException extends GlobalException {
        public ArtistIsNotSameException(String message) {
            super(HttpStatus.CONFLICT, message);
        }

        public ArtistIsNotSameException() {
            super(HttpStatus.CONFLICT, "작곡가가 일치하지 않습니다.");
        }
    }


}
