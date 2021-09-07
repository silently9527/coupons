package cn.silently9527.coupons.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

/**
 * @author starBlues
 * @version 1.0
 */
@Slf4j
public class HttpUtils {

    private HttpUtils(){

    }

    public static HttpServletRequest getRequest(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes == null){
            return null;
        }
        return requestAttributes.getRequest();
    }

    public static String getRemoteAddress(){
        HttpServletRequest request = getRequest();
        if(request == null){
            return "Not found";
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取服务的ip+端口
     * @return http://ip:port
     */
    public static String getServiceAddress(){
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if(requestAttributes == null){
                return "";
            }
            HttpServletRequest request = requestAttributes.getRequest();
            String refererURL = request.getRequestURL().toString();
            try {
                URL url = new URL(refererURL);
                return url.getProtocol() + "://" + url.getHost() + ":" + url.getPort();
            } catch (MalformedURLException e) {
                log.error("转换 url '{}' 异常", refererURL, e);
                return "";
            }
        } catch (Exception e){
            log.error("获取地址异常", e);
            return "";
        }
    }

    /**
     * 路径拼接
     * @param paths 路径集合
     * @return 拼接的结果
     */
    public static String pathJoin(String ...paths) {
        if(paths == null || paths.length == 0){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String path : paths) {
            if(StrUtil.isEmpty(path)){
                continue;
            }
            if(Objects.equals(path, "/")){
                continue;
            }
            if(path.startsWith("/")){
                builder.append(path);
            } else {
                builder.append("/").append(path);
            }
        }
        return builder.toString();
    }

    /**
     * 判断字符串是否为URL
     * @param url url
     * @return true:是URL、false:不是URL
     */
    public static boolean isHttpUrl(String url) {
        if(StrUtil.isEmpty(url)){
            return false;
        }
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            return false;
        }

        if(uri.getHost() == null){
            return false;
        }

        String scheme = uri.getScheme();
        if(scheme == null){
            return false;
        }

        if(scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")){
            return true;
        }

        return false;
    }


}
