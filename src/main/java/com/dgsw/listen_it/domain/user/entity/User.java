package com.dgsw.listen_it.domain.user.entity;


import com.dgsw.listen_it.domain.favorites.entity.Favorite;
import com.dgsw.listen_it.domain.music.entity.Music;
import com.dgsw.listen_it.global.error.GlobalException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor @NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long id;

    private String email;
    private String password;
    private String nickname;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<Music> musicList = new ArrayList<>();

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Favorite> favorites = new ArrayList<>();

    public void addMusic(Music music) {
        musicList.add(music);
        music.setArtist(this);
    }

    public void removeMusic(Music music) {
        musicList.remove(music);
        music.setArtist(null);
    }

    public void addFavorites(Favorite favorite) {
        favorites.add(favorite);
    }

    public void removeFavorites(Favorite favorite) {
        favorites.remove(favorite);
    }

    public void modifyNickname(String nickname) {
        this.nickname = nickname;
    }

    public static class UserNotFoundException extends GlobalException {
        public UserNotFoundException(String message) {
            super(HttpStatus.NOT_FOUND, message);
        }

        public UserNotFoundException() {
            super(HttpStatus.NOT_FOUND, "회원을 찾지 못했습니다.");
        }
    }

    public static class PasswordInvalidException extends GlobalException {
        public PasswordInvalidException(String message) {
            super(HttpStatus.UNAUTHORIZED, message);
        }

        public PasswordInvalidException() {
            super(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
    }

    public static class AuthenticationException extends GlobalException {
        public AuthenticationException(String message) {
            super(HttpStatus.UNAUTHORIZED, message);
        }

        public AuthenticationException() {
            super(HttpStatus.UNAUTHORIZED, "");
        }
    }

}
