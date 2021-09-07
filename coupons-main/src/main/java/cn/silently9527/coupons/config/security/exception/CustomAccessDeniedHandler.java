package cn.silently9527.coupons.config.security.exception;

import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import cn.silently9527.coupons.rest.common.enums.HttpStatusEnum;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义权限处理器
 * @author starBlues
 * @version 1.0
 * @since 2020-12-26
 */
@AllArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final Gson gson;

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException {
        httpServletResponse.setStatus(HttpStatusEnum.FORBIDDEN.getCode());
        httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(
                gson.toJson(Result.response(ApiEnum.OPERATE_ERROR, e.getMessage()))
        );
    }
}
