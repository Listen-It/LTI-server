package com.dgsw.listen_it.domain.user.service;

import com.dgsw.listen_it.domain.user.dto.LoginDto;
import com.dgsw.listen_it.domain.user.dto.NicknameDto;
import com.dgsw.listen_it.domain.user.dto.vo.UserVo;
import com.dgsw.listen_it.domain.user.entity.User;
import com.dgsw.listen_it.domain.user.repository.UserRepository;
import com.dgsw.listen_it.domain.user.ro.TokenRo;
import com.dgsw.listen_it.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;

    @Override
    @Transactional
    public void register(UserVo userVo) {
        if (userRepository.existsByEmail(userVo.getEmail())) {
            throw new User.UserNotFoundException();
        }

        userRepository.save(userVo.toDomain());
    }

    @Override
    public TokenRo login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(User.UserNotFoundException::new);
        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new User.PasswordInvalidException();
        }

        return new TokenRo(tokenProvider.generateAccessToken(user.getId()));
    }

    @Override
    public UserVo findUserByEmail(String email) {
        return new UserVo(userRepository.findByEmail(email)
                .orElseThrow(User.UserNotFoundException::new));
    }

    @Override
    public void modifyNickname(User user, NicknameDto nicknameDto) {
        user.modifyNickname(nicknameDto.getNickname());
        userRepository.save(user);
    }
}
