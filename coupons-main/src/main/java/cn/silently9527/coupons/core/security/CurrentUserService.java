package cn.silently9527.coupons.core.security;

import cn.silently9527.coupons.rest.security.model.param.CurrentUserInfoUpdatedParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 当前用户服务
 * @author starBlues
 * @version 1.0
 */
public interface CurrentUserService {

    /**
     * 修改当前认证用户的密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @throws Exception 修改密码异常
     */
    void updateCurrentUserPassword(String oldPassword, String newPassword) throws Exception;

    /**
     * 登录成功
     * @param userId 用户id
     * @param username 用户名
     * @param ip 登录的ip
     * @param timestamp 时间戳
     */
    void loginSuccess(String userId, String username, String ip, Long timestamp);

    /**
     * 修改头像
     * @param avatarFile 头像文件
     * @return 头像名称
     * @throws Exception 异常
     */
    String updateAvatar(MultipartFile avatarFile) throws Exception;

    /**
     * 获取头像流
     * @param fileName 文件名称
     * @return InputStream
     */
    InputStream getAvatar(String fileName);

    /**
     * 更新当前用户信息
     * @param param 更新参数
     * @throws Exception 更新异常
     */
    void updateInfo(CurrentUserInfoUpdatedParam param) throws Exception;

}
