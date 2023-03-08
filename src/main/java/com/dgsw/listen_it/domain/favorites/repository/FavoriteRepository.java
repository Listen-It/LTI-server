package com.dgsw.listen_it.domain.favorites.repository;

import com.dgsw.listen_it.domain.favorites.entity.Favorite;
import com.dgsw.listen_it.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUserAndMusicId(User user, Long music_id);

    Optional<Favorite> findByUserAndMusicId(User user, Long music_id);

}
