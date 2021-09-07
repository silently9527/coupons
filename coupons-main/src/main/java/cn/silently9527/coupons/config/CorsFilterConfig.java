package cn.silently9527.coupons.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域拦截器配置
 * @author starBlues
 * @version 1.0
 * @since 2020-10-13
 */

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class CorsFilterConfig implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 不使用*，自动适配跨域域名，避免携带Cookie时失效
        String origin = request.getHeader("Origin");
        if(!StrUtil.isEmpty(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }

        // 自适应所有自定义头
        String headers = request.getHeader("Access-Control-Request-Headers");
        if(!StrUtil.isEmpty(headers)) {
            response.setHeader("Access-Control-Allow-Headers", headers);
            response.setHeader("Access-Control-Expose-Headers", headers);
        }

        // 允许跨域的请求方法类型
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
        // 预检命令（OPTIONS）缓存时间，单位：秒
        response.setHeader("Access-Control-Max-Age", "3600");
        // 明确许可客户端发送Cookie，不允许删除字段即可
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            // 如果是OPTIONS预请求, 则响应200
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }


}
