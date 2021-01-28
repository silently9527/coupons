package com.huaan9527.mall.webapi.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huaan9527.mall.webapi.interceptor.ParameterInterceptor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ParameterInterceptor());
    }

    @Bean
    public ContentNegotiatingViewResolver viewResolver(BeanFactory beanFactory) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(beanFactory.getBean(ContentNegotiationManager.class));
        resolver.setDefaultViews(Collections.singletonList(mappingJackson2JsonView()));
        resolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return resolver;
    }

    private MappingJackson2JsonView mappingJackson2JsonView() {
        //设置日期格式
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(smt);

        MappingJackson2JsonView jsonView = new MappingJackson2JsonView(objectMapper);
        jsonView.setExtractValueFromSingleKeyModel(true);
        Set<String> keys = new HashSet<>(Arrays.asList("success", "responseCode", "message", "data"));
        jsonView.setModelKeys(keys);
        return jsonView;
    }


}
