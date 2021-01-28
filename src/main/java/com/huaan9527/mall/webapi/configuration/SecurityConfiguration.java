package com.huaan9527.mall.webapi.configuration;

import com.huaan9527.mall.webapi.security.DefalutLogoutSuccessHandler;
import com.huaan9527.mall.webapi.security.MobileCodeAuthenticationProvider;
import com.huaan9527.mall.webapi.security.MsAuthenticationEntryPoint;
import com.huaan9527.mall.webapi.security.MsAuthenticationFailureHandler;
import com.huaan9527.mall.webapi.security.MsAuthenticationSuccessHandler;
import com.huaan9527.mall.webapi.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.social.security.SocialAuthenticationProvider;

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
