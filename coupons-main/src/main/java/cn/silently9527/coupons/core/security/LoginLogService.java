package cn.silently9527.coupons.core.security;

import cn.silently9527.coupons.repository.databases.entity.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 登录日志 服务类
 * </p>
 *
 * @author starBlues
 * @since 2021-01-29
 */
public interface LoginLogService extends IService<LoginLog> {

    /**
     * 新增登录成功日志
     * @param username 当前用户名
     * @param ip ip 地址
     * @param loginTimestamp 登录时间戳
     */
    void addSuccess(String username, String ip, Long loginTimestamp);

    /**
     * 新增登录失败日志
     * @param username 当前用户名
     * @param ip ip 地址
     * @param loginTimestamp 登录时间戳
     * @param failureMsg 失败原因
     */
    void addFailure(String username, String ip, Long loginTimestamp, String failureMsg);

}
