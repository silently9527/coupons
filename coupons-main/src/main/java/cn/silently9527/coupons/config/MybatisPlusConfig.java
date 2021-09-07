package cn.silently9527.coupons.config;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import cn.silently9527.coupons.core.security.impl.UserServiceImpl;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author starBlues
 * @version 1.0
 * @since 2020-12-24
 */
@Configuration
@MapperScan("cn.silently9527.coupons.repository.databases.mapper")
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }


    @Component
    public static class GmtTimeHandler implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            String data = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_FORMAT);
            this.strictInsertFill(metaObject, "gmtCreated", String.class, data);
            this.strictInsertFill(metaObject, "gmtModified", String.class, data);
            String currentUsername = UserServiceImpl.getCurrentUsername();
            this.strictInsertFill(metaObject, "createUser", String.class, currentUsername);
            this.strictInsertFill(metaObject, "modifiedUser", String.class, currentUsername);
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            this.strictUpdateFill(metaObject, "gmtModified", String.class, DateUtil.format(
                    new Date(), DatePattern.NORM_DATETIME_FORMAT
            ));
            this.strictUpdateFill(metaObject, "modifiedUser", ()-> UserServiceImpl.getCurrentUsername(),
                    String.class);
        }

    }




}
