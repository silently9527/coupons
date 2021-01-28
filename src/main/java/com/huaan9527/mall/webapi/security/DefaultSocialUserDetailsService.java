package com.huaan9527.mall.webapi.security;

import com.huaan9527.mall.webapi.domain.User;
import com.huaan9527.mall.webapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class DefaultSocialUserDetailsService implements SocialUserDetailsService {
    private UserRepository userRepository;

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        User user = userRepository.getById(Long.valueOf(userId));
        if (user == null) {
            return null;
        }
        return new SocialUser(userId, user.getPassword() == null ? "" : user.getPassword(), new ArrayList<>());
    }
}
