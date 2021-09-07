package cn.silently9527.coupons.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * cookie工具类
 *
 * @author starBlues
 * @version 1.0
 */
public class CookieUtils {

    private final static String LOCALHOST = "localhost";

    private CookieUtils(){}

    /**
     * 得到Cookie的值, 没有经过编码
     * @param request httpServletRequest
     * @param cookieName cookie名称
     * @return cookie的值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return getCookieValue(request, cookieName, false);
    }

    /**
     * 得到Cookie的值，可选择是否经过编码
     * @param request httpServletRequest
     * @param cookieName cookie名称
     * @param isDecoder cookie值是否经过编码
     * @return cookie的值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    if (isDecoder) {
                        retValue = URLDecoder.decode(cookieList[i].getValue(), "UTF-8");
                    } else {
                        retValue = cookieList[i].getValue();
                    }
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retValue;
    }

    /**
     *  得到Cookie的值
     * @param request httpServletRequest
     * @param cookieName cookie名称
     * @param encodeString 编码格式
     * @return cookie的值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    retValue = URLDecoder.decode(cookieList[i].getValue(), encodeString);
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retValue;
    }

    /**
     * 设置Cookie的值 不设置生效时间默认浏览器关闭即失效,也不编码
     * @param response HttpServletResponse
     * @param cookieName cookie名称
     * @param cookieValue cookie值
     */
    public static void setCookie(HttpServletResponse response, String cookieName,
                                 String cookieValue) {
        setCookie(response, cookieName, cookieValue, -1);
    }

    /**
     * 设置Cookie的值 在指定时间内生效,但不编码
     * @param response HttpServletResponse
     * @param cookieName cookie名称
     * @param cookieValue cookie值
     * @param cookieMaxAge cookie最大生效时间
     */
    public static void setCookie(HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxAge) {
        setCookie(response, cookieName, cookieValue, cookieMaxAge, false);
    }

    /**
     * 设置Cookie的值 不设置生效时间,但编码
     * @param response HttpServletResponse
     * @param cookieName cookie名称
     * @param cookieValue cookie值
     * @param isEncode 是否编码
     */
    public static void setCookie(HttpServletResponse response, String cookieName,
                                 String cookieValue, boolean isEncode) {
        setCookie(response, cookieName, cookieValue, -1, isEncode);
    }

    /**
     * 设置Cookie的值 在指定时间内生效, 编码参数
     * @param response HttpServletResponse
     * @param cookieName cookie名称
     * @param cookieValue cookie值
     * @param cookieMaxAge 最大生效时间
     * @param isEncode 是否编码
     */
    public static void setCookie(HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxAge, boolean isEncode) {
        doSetCookie(response, cookieName, cookieValue, cookieMaxAge, isEncode);
    }

    /**
     * 设置Cookie的值 在指定时间内生效, 编码参数(指定编码)
     * @param response HttpServletResponse
     * @param cookieName cookie名称
     * @param cookieValue cookie值
     * @param cookieMaxAge 最大生效时间
     * @param encodeString 编码格式
     */
    public static void setCookie(HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxAge, String encodeString) {
        doSetCookie(response, cookieName, cookieValue, cookieMaxAge, encodeString);
    }

    /**
     * 删除Cookie带cookie域名
     * @param response HttpServletResponse
     * @param cookieName cookie名称
     */
    public static void deleteCookie(HttpServletResponse response,
                                    String cookieName) {
        doSetCookie(response, cookieName, null, 0, false);
    }


    /**
     * 设置Cookie的值，并使其在指定时间内生效
     * @param response HttpServletResponse
     * @param cookieName cookie名称
     * @param cookieValue cookie值
     * @param cookieMaxAge cookie生效的最大秒数
     * @param isEncode 是否编码
     */
    private static final void doSetCookie(HttpServletResponse response,
                                          String cookieName, String cookieValue, int cookieMaxAge, boolean isEncode) {
        if(isEncode){
            doSetCookie(response, cookieName, cookieValue, cookieMaxAge, "UTF-8");
        } else {
            doSetCookie(response, cookieName, cookieValue, cookieMaxAge, null);
        }
    }

    /**
     * 设置Cookie的值，并使其在指定时间内生效
     * @param response HttpServletResponse
     * @param cookieName cookie名称
     * @param cookieValue cookie值
     * @param cookieMaxAge cookie生效的最大秒数
     * @param encodeString 编码格式
     */
    private static final void doSetCookie(HttpServletResponse response,
                                          String cookieName, String cookieValue,
                                          int cookieMaxAge, String encodeString) {
        try {
            if(encodeString != null && cookieValue != null){
                cookieValue = URLEncoder.encode(cookieValue, encodeString);
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            cookie.setMaxAge(cookieMaxAge);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
