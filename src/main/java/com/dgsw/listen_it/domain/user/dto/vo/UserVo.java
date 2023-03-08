package com.dgsw.listen_it.domain.user.dto.vo;

import com.dgsw.listen_it.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class UserVo {

    private String email;
    private String password;
    private String nickname;

    public User toDomain() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .build();
    }

    public UserVo(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.password = user.getPassword();
    }
}
