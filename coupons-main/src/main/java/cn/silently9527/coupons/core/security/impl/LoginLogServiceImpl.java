package cn.silently9527.coupons.core.security.impl;

import cn.silently9527.coupons.core.security.LoginLogService;
import cn.silently9527.coupons.repository.databases.entity.LoginLog;
import cn.silently9527.coupons.repository.databases.mapper.LoginLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.silently9527.coupons.utils.TimeUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录日志 服务实现类
 * </p>
 *
 * @author starBlues
 * @since 2021-01-29
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    // TODO 自行实现ip转换物理地址
    private String getAddressByIp(String ip){
        return "无";
    }

    @Override
    public void addSuccess(String username, String ip, Long loginTimestamp) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setLoginIp(ip);
        loginLog.setLoginResult(1);
        loginLog.setLoginAddress(getAddressByIp(ip));
        setTime(loginLog, loginTimestamp);
        save(loginLog);
    }

    @Override
    public void addFailure(String username, String ip, Long loginTimestamp, String failureMsg) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setLoginIp(ip);
        loginLog.setLoginResult(2);
        loginLog.setLoginFailureMsg(failureMsg);
        loginLog.setLoginAddress(getAddressByIp(ip));
        setTime(loginLog, loginTimestamp);
        save(loginLog);
    }


    private void setTime(LoginLog loginLog, Long loginTimestamp){
        try {
            loginLog.setGmtLoginTimestamp(loginTimestamp);
            loginLog.setGmtLoginTime(TimeUtil.formatTime(loginTimestamp));
        } catch (Exception e){
            loginLog.setGmtLoginTimestamp(TimeUtil.getNowTimeStamp());
            loginLog.setGmtLoginTime(TimeUtil.getNowTimeToSeconds());
        }
    }

}
