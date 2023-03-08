package com.dgsw.listen_it.domain.user.controller;

import com.dgsw.listen_it.domain.user.dto.LoginDto;
import com.dgsw.listen_it.domain.user.dto.NicknameDto;
import com.dgsw.listen_it.domain.user.dto.vo.UserVo;
import com.dgsw.listen_it.domain.user.entity.User;
import com.dgsw.listen_it.domain.user.ro.TokenRo;
import com.dgsw.listen_it.domain.user.service.UserService;
import com.dgsw.listen_it.global.annotations.Certification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void userRegister(@RequestBody UserVo userVo) {
        userService.register(userVo);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenRo userLogin(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @GetMapping
    public UserVo userFind(@RequestParam String email) {
        return userService.findUserByEmail(email);
    }

    @PatchMapping
    @Certification
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeNickname(@RequestAttribute("user") User user,
                               @RequestBody NicknameDto nicknameDto) {
        userService.modifyNickname(user, nicknameDto);
    }
}
