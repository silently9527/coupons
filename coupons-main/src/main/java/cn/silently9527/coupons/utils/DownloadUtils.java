package cn.silently9527.coupons.utils;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 下载工具类
 * @author starBlues
 * @version 1.0
 */
public class DownloadUtils {


    public static void downloadFile(HttpServletResponse response, File file) {
        downloadFile(response, file, file.getName());
    }

    public static void downloadFile(HttpServletResponse response, File file, String fileName) {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream;charset=UTF-8");
        if(StrUtil.isEmpty(fileName)){
            fileName = file.getName();
        }
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        try {
           fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (Exception e){
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition", "attachment;filename="+fileName);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            byte[] outputByte = new byte[1024];
            int readTmp = 0;
            while ((readTmp = fis.read(outputByte)) != -1) {
                os.write(outputByte, 0, readTmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
