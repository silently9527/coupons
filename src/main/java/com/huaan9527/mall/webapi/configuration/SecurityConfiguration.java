package com.huaan9527.mall.webapi.configuration;

import com.huaan9527.mall.webapi.security.DefalutLogoutSuccessHandler;
import com.huaan9527.mall.webapi.security.MobileCodeAuthenticationProvider;
import com.huaan9527.mall.webapi.security.MsAuthenticationEntryPoint;
import com.huaan9527.mall.webapi.security.MsAuthenticationFailureHandler;
import com.huaan9527.mall.webapi.security.MsAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MobileCodeAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.apply(new SocialConfigurer());
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/st/**", "/mi/**", "/auth/**", "/favicon.ico", "/login", "/logout")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("mobile")
                .passwordParameter("code")
                .successHandler(new MsAuthenticationSuccessHandler())
                .failureHandler(new MsAuthenticationFailureHandler())
                .and()
                .logout()
                .logoutSuccessHandler(new DefalutLogoutSuccessHandler())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new MsAuthenticationEntryPoint());

        http.authenticationProvider(authenticationProvider);
    }

}
