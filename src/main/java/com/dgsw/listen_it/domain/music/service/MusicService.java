package com.dgsw.listen_it.domain.music.service;

import com.dgsw.listen_it.domain.music.constraints.Category;
import com.dgsw.listen_it.domain.music.dto.MusicDto;
import com.dgsw.listen_it.domain.music.ro.MusicRo;
import com.dgsw.listen_it.domain.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MusicService {

    void uploadMusic(User user, MultipartFile musicFile, MultipartFile albumFile, MusicDto musicDto);

    void deleteMusic(User user, long id);

    void changeTitle(User user, long id, MusicDto musicDto);

    MusicRo findMusicById(long id);

    Page<MusicRo> findMusicByTitle(String title, int page);

    Page<MusicRo> findMusicList(Category category, int page);

    List<MusicRo> findMyMusic(User user);

    void download(String type, String directory, String filename, HttpServletResponse response);

    void changeAlbumPicture(User user, MultipartFile file, long id);

}
