package cn.silently9527.coupons.core.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
public class ScheduledTaskRegistrar {
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private final Map<String, ScheduledFuture<?>> futureGroup = new ConcurrentHashMap<>(16);
    private final Map<String, Runnable> taskGroup = new ConcurrentHashMap<>(16);

    @PostConstruct
    public void init() {
        this.threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        this.threadPoolTaskScheduler.initialize();
    }

    /**
     * 启动定时任务 （若任务名相同，则覆盖之前的任务）
     **/
    public void startCron(String name, Runnable task, String cron) {
        stopCron(name);
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(task, new CronTrigger(cron));
        futureGroup.put(name, future);
        taskGroup.put(name, task);
        log.info(String.format("scheduled task: [%s] start success", name));
    }


    /**
     * 停止任务
     **/
    public void stopCron(String name) {
        ScheduledFuture<?> future = futureGroup.get(name);
        if (future != null) {
            future.cancel(true);
            log.info(String.format("scheduled task: [%s] stop success", name));
        } else {
            log.error(String.format("scheduled task: [%s] not exist", name));
        }
    }


    /**
     * 变更任务间隔，再次启动 先停止，在开启.
     **/
    public void changeCron(String name, String cron) {
        stopCron(name);
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(taskGroup.get(name), new CronTrigger(cron));
        futureGroup.put(name, future);
        log.info(String.format("scheduled task: [%s] change success", name));
    }

}
