package cn.silently9527.coupons.config.security;

import cn.silently9527.coupons.core.security.LoginLogService;
import cn.silently9527.coupons.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * 授权失败监听者
 * @author starBlues
 * @version 1.0
 */
@Component
public class AuthFailureListener implements ApplicationListener<AbstractAuthenticationFailureEvent>{

    private final ExecutorService executorService;
    private final LoginLogService loginLogService;

    public AuthFailureListener(@Qualifier("system") ExecutorService executorService,
                               LoginLogService loginLogService) {
        this.executorService = executorService;
        this.loginLogService = loginLogService;
    }

    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent auth) {
        String ip = HttpUtils.getRemoteAddress();
        Object username = auth.getAuthentication().getPrincipal();
        String s = String.valueOf(username);
        if(Objects.equals(s, "access-token")){
            return;
        }
        executorService.execute(()->{
            loginLogService.addFailure(String.valueOf(username), ip, auth.getTimestamp(),
                    auth.getException().getLocalizedMessage());
        });
    }
}
