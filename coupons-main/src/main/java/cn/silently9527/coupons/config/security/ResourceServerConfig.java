package cn.silently9527.coupons.config.security;


import cn.silently9527.coupons.config.prop.SystemProp;
import cn.silently9527.coupons.config.security.exception.AuthExceptionEntryPoint;
import cn.silently9527.coupons.config.security.exception.CustomAccessDeniedHandler;
import com.google.gson.Gson;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.Set;


/**
 * 资源服务配置
 * @author starBlues
 * @version 1.0
 * @since 2020-12-26
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final SystemProp systemProp;
    private AuthenticationEntryPoint authExceptionEntryPoint;
    private AccessDeniedHandler accessDeniedHandler;


    public ResourceServerConfig(Gson gson, SystemProp systemProp){
        this.systemProp = systemProp;
        authExceptionEntryPoint = new AuthExceptionEntryPoint(gson);
        accessDeniedHandler = new CustomAccessDeniedHandler(gson);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry =
                http
                        .cors().and().csrf().disable()
                        .headers().frameOptions().disable()
                        .and()
                        .authorizeRequests()
                        // 取消 swagger 接口认证
                        .antMatchers("/doc.html", "/webjars/**", "/swagger-resources/**",
                                "/v2/api-docs", "/v2/api-docs-ext","/api/plugins/*/mi/**")
                        // 禁用以 iframe 方式访问界面
                        .permitAll();

        configure(registry);
        // /api 开头的需要进行认证
        registry.antMatchers("/api/**")
                .authenticated();
    }

    protected void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        Set<String> noAuthPathPatterns = systemProp.getNoAuthPathPatterns();
        if(noAuthPathPatterns == null || noAuthPathPatterns.isEmpty()){
            return;
        }
        String[] noAuthPathPatternArray = noAuthPathPatterns.toArray(new String[0]);
        registry.antMatchers(noAuthPathPatternArray).permitAll();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(authExceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }

}
