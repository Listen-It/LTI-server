package com.dgsw.listen_it.domain.favorites.contoller;

import com.dgsw.listen_it.domain.favorites.service.FavoriteService;
import com.dgsw.listen_it.domain.music.ro.ListRo;
import com.dgsw.listen_it.domain.music.ro.MusicRo;
import com.dgsw.listen_it.domain.user.entity.User;
import com.dgsw.listen_it.global.annotations.Certification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Certification
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void setFavorite(@PathVariable("id") long musicId,
                            @RequestAttribute("user") User user) {
        favoriteService.setBookmark(user, musicId);
    }

    @Certification
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavorite(@PathVariable("id") long musicId,
                               @RequestAttribute("user") User user) {
        favoriteService.removeBookmark(user, musicId);
    }

    @GetMapping
    @Certification
    public ListRo<MusicRo> getFavorites(@RequestAttribute("user") User user) {
        return new ListRo<>(favoriteService.getBookmarkList(user));
    }
}
