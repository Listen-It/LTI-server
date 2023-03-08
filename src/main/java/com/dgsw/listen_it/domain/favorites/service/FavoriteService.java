package com.dgsw.listen_it.domain.favorites.service;

import com.dgsw.listen_it.domain.music.ro.MusicRo;
import com.dgsw.listen_it.domain.user.entity.User;

import java.util.List;

public interface FavoriteService {

    void setBookmark(User user, long musicId);

    void removeBookmark(User user, long musicId);

    List<MusicRo> getBookmarkList(User user);
}
