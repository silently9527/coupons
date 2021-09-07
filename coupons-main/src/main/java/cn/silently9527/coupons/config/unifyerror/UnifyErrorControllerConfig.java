package cn.silently9527.coupons.config.unifyerror;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;
import java.util.List;

/**
 * 统一错误结果json配置
 * @author starBlues
 * @version 1.0
 * @since 2020-08-17
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
public class UnifyErrorControllerConfig {


    @Autowired(required = false)
    private List<ErrorViewResolver> errorViewResolvers;
    private final ServerProperties serverProperties;

    public UnifyErrorControllerConfig(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Bean
    public UnifyErrorController basicErrorController(ErrorAttributes errorAttributes) {
        return new UnifyErrorController(errorAttributes, this.serverProperties.getError(),
                this.errorViewResolvers);
    }

}
