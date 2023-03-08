package com.dgsw.listen_it.domain.music.service;

import com.dgsw.listen_it.domain.music.constraints.Category;
import com.dgsw.listen_it.domain.music.dto.MusicDto;
import com.dgsw.listen_it.domain.music.entity.Music;
import com.dgsw.listen_it.domain.music.repository.MusicRepository;
import com.dgsw.listen_it.domain.music.ro.MusicRo;
import com.dgsw.listen_it.domain.user.entity.User;
import com.dgsw.listen_it.domain.user.repository.UserRepository;
import com.dgsw.listen_it.global.utils.LocalFileUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final UserRepository userRepository;
    private final MusicRepository musicRepository;

    @Override
    public void uploadMusic(User user, MultipartFile musicFile, MultipartFile albumFile, MusicDto musicDto) {
        final String title = musicDto.getTitle();
        if (musicRepository.existsByArtistAndTitle(user, title)) {
            throw new Music.AlreadyHasSameTitleException();
        }

        LocalFileUtils musicFileUtils = LocalFileUtils.getInstance(LocalFileUtils.Type.MUSIC);
        String musicFilename = musicFileUtils.saveFile(musicFile);
        LocalFileUtils albumFileUtils = LocalFileUtils.getInstance(LocalFileUtils.Type.ALBUM);
        String albumFilename = albumFileUtils.saveFile(albumFile);

        Music music = Music.builder()
                .filename(musicFilename)
                .albumPicture(albumFilename)
                .title(title)
                .build();
        user.addMusic(music);
        musicRepository.save(music);
        userRepository.save(user);
    }

    @Override
    public void changeAlbumPicture(User user, MultipartFile file, long id) {
        Music music = musicRepository.findById(id)
                .orElseThrow(Music.MusicNotFoundException::new);
        if (!music.getArtist().equals(user)) {
            throw new Music.ArtistIsNotSameException();
        }

        LocalFileUtils fileUtils = LocalFileUtils.getInstance(LocalFileUtils.Type.ALBUM);
        String filename = fileUtils.saveFile(file);
        music.modifyAlbumPicture(filename);
        musicRepository.save(music);
    }

    @Override
    public void deleteMusic(User user, long id) {
        Music music = musicRepository.findById(id)
                .orElseThrow(Music.MusicNotFoundException::new);
        user.removeMusic(music);
        musicRepository.delete(music);
        userRepository.save(user);
    }

    @Override
    public void changeTitle(User user, long id, MusicDto musicDto) {
        Music music = musicRepository.findById(id)
                .orElseThrow(Music.MusicNotFoundException::new);
        if (!music.getArtist().equals(user)) {
            throw new Music.ArtistIsNotSameException();
        }

        music.modifyTitle(musicDto.getTitle());
        musicRepository.save(music);
    }

    @Override
    public MusicRo findMusicById(long id) {
        return new MusicRo(musicRepository.findById(id)
                .orElseThrow(Music.MusicNotFoundException::new));
    }

    @Override
    public Page<MusicRo> findMusicByTitle(String title, int page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());
        return new PageImpl<>(musicRepository.findAllByTitleContains(title, pageRequest)
                .stream().map(MusicRo::new).collect(Collectors.toList()));
    }

    @Override
    public Page<MusicRo> findMusicList(Category category, int page) {
        if (category == Category.LATEST) {
            PageRequest pageRequest = PageRequest.of(page, 10, sortBy(category));
            return new PageImpl<>(musicRepository.findAll(pageRequest)
                    .stream().map(MusicRo::new).collect(Collectors.toList()));
        }

        List<Music> musicList = musicRepository.findAll();
        List<Music> topMusic = musicList.stream().sorted((o1, o2) -> {
            int a = o1.getFavorites().size();
            int b = o2.getFavorites().size();

            return Integer.compare(a, b);
        }).limit(10).collect(Collectors.toList());
        return new PageImpl<>(topMusic.stream().map(MusicRo::new).collect(Collectors.toList()));
    }

    private Sort sortBy(Category category) {
        final Sort sort = Sort.by(category.getTopic());
        if (category.isAscending()) {
            return sort.ascending();
        }
        return sort.descending();
    }

    @Override
    public List<MusicRo> findMyMusic(User user) {
        return musicRepository.findAllByArtist(user)
                .stream().map(MusicRo::new).collect(Collectors.toList());
    }

    @Override
    public void download(String type, String directory, String filename, HttpServletResponse response) {
        final LocalFileUtils.Type fileType;
        if (type.equalsIgnoreCase("sounds")) {
            fileType = LocalFileUtils.Type.MUSIC;
        }
        else {
            fileType = LocalFileUtils.Type.ALBUM;
        }

        LocalFileUtils fileUtils = LocalFileUtils.getInstance(fileType);

        String path = new File("").getAbsolutePath() + "\\" + fileUtils.getPath()
                + directory + "\\" + filename;
        File file = new File(path);
        if (!file.exists()) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }

        response.setHeader("Content-Disposition", "attachment;filename=" + filename);

        try (final FileInputStream stream = new FileInputStream(path)) {
            OutputStream outputStream = response.getOutputStream();

            int read;
            byte[] buffer = new byte[1024];
            while ((read = stream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
