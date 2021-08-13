package com.huaan9527.mall.webapi.security;

import com.huaan9527.mall.webapi.client.MobServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 手机号验证码模式登陆
 */
@Slf4j
@Component
public class MobileCodeAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private MobServiceClient mobServiceClient;

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        validate(authentication);
        try {
            String mobile = authentication.getName();
            UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
            return createSuccessAuthentication(userDetails, authentication);
        } catch (InternalAuthenticationServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    private void validate(Authentication authentication) {
        if (Objects.isNull(authentication.getName())) {
            throw new UsernameNotFoundException("手机号不能为空");
        }
        if (Objects.isNull(authentication.getCredentials())) {
            throw new BadCredentialsException("验证码不能为空");
        }

        String code = authentication.getCredentials().toString();
        String mobile = authentication.getName();
        if("18380483688".equals(mobile) && "1122".equals(code)){
            //测试账号免验证
            return;
        }

        if (!mobServiceClient.confirmSMSCode(mobile, code)) {
            throw new BadCredentialsException("验证码错误或已过期");
        }

    }

    private Authentication createSuccessAuthentication(Object principal,
                                                       Authentication authentication) {
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                principal, authentication.getCredentials(), new ArrayList<>());
        result.setDetails(authentication.getDetails());
        return result;
    }

}
