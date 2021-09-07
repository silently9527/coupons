package cn.silently9527.coupons.config.security;

import cn.silently9527.coupons.core.security.CurrentUserService;
import cn.silently9527.coupons.core.security.model.UserDetailsModel;
import cn.silently9527.coupons.repository.databases.entity.User;
import cn.silently9527.coupons.utils.HttpUtils;
import cn.silently9527.coupons.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * 授权成功监听者
 * @author starBlues
 * @version 1.0
 */
@Component
public class AuthSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final ExecutorService executorService;
    private final CurrentUserService currentUserService;

    public AuthSuccessListener(@Qualifier("system") ExecutorService executorService,
                               CurrentUserService currentUserService) {
        this.executorService = executorService;
        this.currentUserService = currentUserService;
    }


    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent auth) {
        String ip = HttpUtils.getRemoteAddress();
        Object principal = auth.getAuthentication().getPrincipal();
        if(principal instanceof UserDetailsModel){
            UserDetailsModel userDetailsModel = (UserDetailsModel) principal;
            User user = userDetailsModel.getUser();
            if(user == null){
                return;
            }
            executorService.execute(()->{
                currentUserService.loginSuccess(user.getUserId(), user.getUsername(),
                        ip, TimeUtil.getNowTimeStamp());
            });
        }
    }
}
