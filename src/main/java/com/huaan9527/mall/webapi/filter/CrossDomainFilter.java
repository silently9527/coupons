package com.huaan9527.mall.webapi.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//@Order(0)
//@WebFilter(filterName = "crossDomainFilter", urlPatterns = "/*")
public class CrossDomainFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(CrossDomainFilter.class);

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 得到请求的uri和url
        String reqUri = req.getRequestURI();
        String reqUrl = req.getRequestURL().toString();
        log.debug("reqUri:{} ,reqUrl:{}", reqUri, reqUrl);

        // 跨域处理
        this.crossDomain(req, resp);
        chain.doFilter(req, resp);

    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    /**
     * 处理跨域问题
     */
    private void crossDomain(HttpServletRequest request, HttpServletResponse response) {
        String originHeader = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

}