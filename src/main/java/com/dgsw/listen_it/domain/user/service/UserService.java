package com.dgsw.listen_it.domain.user.service;

import com.dgsw.listen_it.domain.user.dto.LoginDto;
import com.dgsw.listen_it.domain.user.dto.NicknameDto;
import com.dgsw.listen_it.domain.user.dto.vo.UserVo;
import com.dgsw.listen_it.domain.user.entity.User;
import com.dgsw.listen_it.domain.user.ro.TokenRo;

public interface UserService {

    void register(UserVo userVo);

    TokenRo login(LoginDto loginDto);

    UserVo findUserByEmail(String email);

    void modifyNickname(User user, NicknameDto nicknameDto);

}
