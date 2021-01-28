package com.huaan9527.mall.webapi.security;

import com.huaan9527.mall.webapi.utils.ResponseEntity;
import com.huaan9527.mall.webapi.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MsAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Map<String, Object> data = new HashMap<>();
        data.put("xAuthToken", request.getSession().getId());
        ResponseEntity responseEntity = ResponseEntity.success(data);

        WebUtils.writeJSON(response, responseEntity);
    }
}
