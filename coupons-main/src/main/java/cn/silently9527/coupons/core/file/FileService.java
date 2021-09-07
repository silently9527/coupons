package cn.silently9527.coupons.core.file;

import java.io.InputStream;

/**
 * 文件接口
 * @author starBlues
 * @version 1.0
 */
public interface FileService {


    /**
     * 保存头像
     * @param fileName 文件名称
     * @param inputStream 输入流
     */
    void saveAvatar(String fileName, InputStream inputStream);

    /**
     * 得到头像的输入流
     * @param avatarFileName 头像文件名称
     * @return
     */
    InputStream getAvatar(String avatarFileName);

}
