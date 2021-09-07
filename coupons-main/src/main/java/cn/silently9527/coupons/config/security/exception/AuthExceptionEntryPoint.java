package cn.silently9527.coupons.config.security.exception;

import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.OAuth2Enum;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权错误处理
 * @author starBlues
 * @version 1.0
 */
@AllArgsConstructor
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

    private final Gson gson;

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {

        Throwable cause = e.getCause();
        Result<String> result = Result.response(OAuth2Enum.FAILURE, e.getMessage());
        if(cause instanceof InvalidTokenException){
            // 无效的Token 异常
            String message = cause.getMessage();
            if(message.contains("Access token expired")){
                // Token 过期
                result = Result.response(OAuth2Enum.TOKEN_EXPIRED);
            } else {
                result = Result.response(OAuth2Enum.INVALID_TOKEN);
            }
        }
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(gson.toJson(result));
    }
}
