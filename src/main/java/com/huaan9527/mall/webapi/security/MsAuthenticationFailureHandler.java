package com.huaan9527.mall.webapi.security;

import com.huaan9527.mall.webapi.utils.Constants;
import com.huaan9527.mall.webapi.utils.ResponseEntity;
import com.huaan9527.mall.webapi.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MsAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("Authentication Fail, request uri:[{}]", request.getRequestURI(), exception);
        String message = "登录失败";
        if(exception instanceof BadCredentialsException){
            message = "验证码错误或已过期";
        } else if(exception instanceof LockedException){
            message = "账号已被禁用，请联系客服";
        }
        WebUtils.writeJSON(response, ResponseEntity.failure(Constants.LOGIN_FAILURE_CODE, message));
    }
}
