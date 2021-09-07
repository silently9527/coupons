package cn.silently9527.coupons.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author starBlues
 * @version 1.0
 */
@Configuration
@Slf4j
public class ThreadConfig {


    @Bean("system")
    public ExecutorService executorService(){
        return Executors.newCachedThreadPool(new ThreadFactoryBuilder()
                .setNameFormat("SystemExecutor-%d")
                .setDaemon(false)
                .setUncaughtExceptionHandler(new LogUncaughtExceptionHandler(log))
                .build()
        );
    }


    public static class LogUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
        private final Logger log;

        public LogUncaughtExceptionHandler(Logger log) {
            this.log = log;
        }

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            log.error("Thread {} failed by not catching exception: {}.", t.getName(), e);
        }
    }

}
