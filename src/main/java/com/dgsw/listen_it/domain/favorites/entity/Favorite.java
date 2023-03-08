package com.dgsw.listen_it.domain.favorites.entity;

import com.dgsw.listen_it.domain.music.entity.Music;
import com.dgsw.listen_it.domain.user.entity.User;
import com.dgsw.listen_it.global.error.GlobalException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Entity
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Favorite {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "music_id", updatable = false)
    private Music music;

    public static class AlreadyBookmarkedException extends GlobalException {
        public AlreadyBookmarkedException(String message) {
            super(HttpStatus.CONFLICT, message);
        }

        public AlreadyBookmarkedException() {
            super(HttpStatus.CONFLICT, "이미 해당 음원을 저장하였습니다.");
        }
    }

    public static class NotBookmarkedSuchMusicException extends GlobalException {
        public NotBookmarkedSuchMusicException(String message) {
            super(HttpStatus.CONFLICT, message);
        }

        public NotBookmarkedSuchMusicException() {
            super(HttpStatus.CONFLICT, "해당 음원은 저장되어있지 않습니다.");
        }
    }
}
