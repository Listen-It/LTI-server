package com.dgsw.listen_it.domain.favorites.service;

import com.dgsw.listen_it.domain.favorites.entity.Favorite;
import com.dgsw.listen_it.domain.favorites.repository.FavoriteRepository;
import com.dgsw.listen_it.domain.music.entity.Music;
import com.dgsw.listen_it.domain.music.repository.MusicRepository;
import com.dgsw.listen_it.domain.music.ro.MusicRo;
import com.dgsw.listen_it.domain.user.entity.User;
import com.dgsw.listen_it.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MusicRepository musicRepository;
    private final UserRepository userRepository;

    @Override
    public void setBookmark(User user, long musicId) {
        if (favoriteRepository.existsByUserAndMusicId(user, musicId)) {
            throw new Favorite.AlreadyBookmarkedException();
        }
        Music music = musicRepository.findById(musicId)
                .orElseThrow(Music.MusicNotFoundException::new);

        Favorite favorite = Favorite.builder()
                .music(music)
                .user(user)
                .build();
        music.addFavorite(favorite);
        user.addFavorites(favorite);
        favoriteRepository.save(favorite);
        musicRepository.save(music);
        userRepository.save(user);
    }

    @Override
    public void removeBookmark(User user, long musicId) {
        Favorite favorite = favoriteRepository.findByUserAndMusicId(user, musicId)
                .orElseThrow(Favorite.NotBookmarkedSuchMusicException::new);
        favoriteRepository.delete(favorite);
    }

    @Override
    public List<MusicRo> getBookmarkList(User user) {
        return user.getFavorites().stream()
                .map(Favorite::getMusic)
                .map(MusicRo::new)
                .collect(Collectors.toList());
    }
}
