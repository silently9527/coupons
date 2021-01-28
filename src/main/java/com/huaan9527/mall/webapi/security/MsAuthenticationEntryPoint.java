package com.huaan9527.mall.webapi.security;

import com.huaan9527.mall.webapi.utils.Constants;
import com.huaan9527.mall.webapi.utils.ResponseEntity;
import com.huaan9527.mall.webapi.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MsAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public MsAuthenticationEntryPoint() {
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error(authException.getMessage());
        WebUtils.writeJSON(response, ResponseEntity.failure(Constants.UNLOGIN_CODE, "还未登录请先登录"));
    }
}
