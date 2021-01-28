package com.huaan9527.mall.webapi.security;

import com.huaan9527.mall.webapi.exception.MsException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUser;

public class SecurityUtils {
    /**
     * @return
     */
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return -1L;
        }
        if (authentication.getPrincipal() instanceof SocialUser) {
            return Long.valueOf(((SocialUser) authentication.getPrincipal()).getUserId());
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            return Long.valueOf(((UserDetails) authentication.getPrincipal()).getUsername());
        }
        throw new MsException("User", "当前登录账号异常");
    }

}
