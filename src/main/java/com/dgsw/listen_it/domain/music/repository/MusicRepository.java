package com.dgsw.listen_it.domain.music.repository;

import com.dgsw.listen_it.domain.music.entity.Music;
import com.dgsw.listen_it.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {

    boolean existsByArtistAndTitle(User artist, String title);

    Optional<Music> findByArtistAndTitle(User artist, String title);

    List<Music> findAllByArtist(User user);

    Page<Music> findAllByTitleContains(String title, Pageable pageable);

}
