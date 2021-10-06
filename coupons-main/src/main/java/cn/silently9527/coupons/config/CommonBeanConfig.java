package cn.silently9527.coupons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author starBlues
 * @version 1.0
 * @since 2020-12-24
 */
@Configuration
public class CommonBeanConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
