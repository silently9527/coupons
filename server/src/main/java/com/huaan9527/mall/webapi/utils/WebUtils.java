package com.huaan9527.mall.webapi.utils;


import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class WebUtils {
    private WebUtils() {
    }

    /**
     * <p>转为unicode 编码<p>
     *
     * @param string
     * @return unicodeString
     */
    public static String encode(String str) {
        String prifix = "\\u";
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            String code = prifix + format(Integer.toHexString(c));
            unicode.append(code);
        }
        return unicode.toString();
    }

    /**
     * <p>unicode 解码<p>
     *
     * @param unicode
     * @return originalString
     */
    public static String decode(String unicode) {
        StringBuffer str = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            str.append((char) data);
        }
        return str.length() > 0 ? str.toString() : unicode;
    }

    /**
     * 为长度不足4位的unicode 值补零
     *
     * @param str
     * @return
     */
    private static String format(String str) {
        for (int i = 0, l = 4 - str.length(); i < l; i++)
            str = "0" + str;
        return str;
    }


    public static String clientIp(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        } else {
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }

            return ip;
        }
    }

    public static boolean isMobileBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        if (!StringUtils.isNotEmpty(userAgent)) {
            return false;
        } else {
            String lowerAgent = userAgent.toLowerCase();
            return lowerAgent.contains("mobile") || lowerAgent.contains("android") || lowerAgent.contains("ios");
        }
    }

    public static String encodeChineseDownloadFileName(HttpServletRequest request, String pFileName) {
        String agent = request.getHeader("USER-AGENT");

        try {
            if (null != agent && agent.contains("MSIE")) {
                pFileName = URLEncoder.encode(pFileName, "utf-8");
            } else {
                pFileName = new String(pFileName.getBytes("utf-8"), "iso8859-1");
            }
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
        }

        return pFileName;
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    public static void writeJSON(ServletResponse response, Object data) {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter pw = null;

        try {
            pw = resp.getWriter();
            pw.write(JSON.toJSONString(data));
            pw.close();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    public static String getServletBasePath(HttpServletRequest req) {
        StringBuilder baseUrl = new StringBuilder();
        String scheme = req.getScheme();
        int port = req.getServerPort();
        baseUrl.append(scheme);
        baseUrl.append("://");
        baseUrl.append(req.getServerName());
        if (scheme.equals("http") && port != 80 || scheme.equals("https") && port != 443) {
            baseUrl.append(':');
            baseUrl.append(req.getServerPort());
            baseUrl.append(req.getContextPath());
        }

        return baseUrl.toString();
    }
}
