package com.huaan9527.mall.webapi.security;

import com.huaan9527.mall.webapi.utils.ResponseEntity;
import com.huaan9527.mall.webapi.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefalutLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        WebUtils.writeJSON(response, ResponseEntity.success("退出成功"));
    }
}
