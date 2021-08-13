package com.huaan9527.mall.webapi;

import com.huaan9527.mall.webapi.configuration.properties.DaTaoKeProperties;
import com.huaan9527.mall.webapi.configuration.properties.TaoBaoKeProperties;
import com.huaan9527.mall.webapi.configuration.properties.TencentSmsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.mybatis.repository.config.EnableMybatisAuditing;
import org.springframework.social.config.annotation.EnableSocial;

@EnableSocial
@EnableMybatisAuditing
@SpringBootApplication
@ServletComponentScan(basePackages = {"com.huaan9527.mall.webapi.filter"})
@EnableConfigurationProperties({DaTaoKeProperties.class, TaoBaoKeProperties.class, TencentSmsProperties.class})
public class MallWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallWebapiApplication.class, args);
    }

}
