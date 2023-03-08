package com.dgsw.listen_it.domain.music.controller;

import com.dgsw.listen_it.domain.music.constraints.Category;
import com.dgsw.listen_it.domain.music.dto.MusicDto;
import com.dgsw.listen_it.domain.music.ro.ListRo;
import com.dgsw.listen_it.domain.music.ro.MusicRo;
import com.dgsw.listen_it.domain.music.service.MusicService;
import com.dgsw.listen_it.domain.user.entity.User;
import com.dgsw.listen_it.global.annotations.Certification;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class MusicController {

    private final MusicService musicService;

    @Certification
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void upload(@RequestAttribute("user") User user,
                       @RequestPart("sound-file") MultipartFile soundFile,
                       @RequestPart("album-file") MultipartFile albumFile,
                       @RequestPart MusicDto info) {
        musicService.uploadMusic(user, soundFile, albumFile, info);
    }

    @Certification
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id, @RequestAttribute("user") User user) {
        musicService.deleteMusic(user, id);
    }

    @Certification
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeTitle(@PathVariable long id, @RequestAttribute("user") User user,
                            @RequestBody MusicDto musicDto) {
        musicService.changeTitle(user, id, musicDto);
    }

    @GetMapping("/{id}")
    public MusicRo findMusic(@PathVariable long id) {
        return musicService.findMusicById(id);
    }

    @GetMapping
    public Page<MusicRo> findMusicByTitle(@RequestParam String title,
                                          @RequestParam(defaultValue = "0") int page) {
        return musicService.findMusicByTitle(title, page);
    }

    @GetMapping("/category")
    public Page<MusicRo> findMusicByCategory(@RequestParam Category type,
                                             @RequestParam(defaultValue = "0") int page) {
        return musicService.findMusicList(type, page);
    }

    @Certification
    @GetMapping("/my-soundtrack")
    public ListRo<MusicRo> findMySoundtrack(@RequestAttribute("user") User user) {
        return new ListRo<>(musicService.findMyMusic(user));
    }

    @GetMapping("/download/{type}/{directory}/{filename}")
    public void downloadSoundFile(@PathVariable String type,
                                  @PathVariable String directory,
                                  @PathVariable String filename, HttpServletResponse response) {
        musicService.download(type, directory, filename, response);
    }

    @Certification
    @PatchMapping(value ="/{id}/cover", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void changeAlbumCover(@PathVariable long id, @RequestAttribute("user") User user,
                                 @RequestPart("album-file") MultipartFile albumFile) {
        musicService.changeAlbumPicture(user, albumFile, id);
    }
}
