package com.huaan9527.mall.webapi.security;

import com.huaan9527.mall.webapi.domain.User;
import com.huaan9527.mall.webapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DefaultConnectionSignUp implements ConnectionSignUp {
    private UserService userService;

    @Override
    public String execute(Connection<?> connection) {
        User user = userService.signUp(connection);
        return String.valueOf(user.getId());
    }
}
