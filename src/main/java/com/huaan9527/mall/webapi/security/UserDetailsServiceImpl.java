package com.huaan9527.mall.webapi.security;

import com.huaan9527.mall.webapi.service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Component
@NoArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        com.huaan9527.mall.webapi.domain.User user = userService.findByMobile(mobile);
        if (Objects.isNull(user)) {
            log.error("手机号[{}]查询不到用户，走注册流程", mobile);
            user = userService.register(mobile);
        }
        assert user.getId() != null;
        return new User(user.getId().toString(), user.getPassword(), new ArrayList<>());
    }

}