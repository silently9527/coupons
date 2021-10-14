package cn.silently9527.coupons.plugincenter.schedule;

import cn.silently9527.coupons.core.schedule.ScheduledTaskRegistrar;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class UpdatePluginPasswordTask {
    private ScheduledTaskRegistrar scheduledTaskRegistrar;

    @PostConstruct
    public void init() {
        scheduledTaskRegistrar.startCron("UpdatePluginPasswordTask", () -> System.out.println("UpdatePluginPasswordTask....."), "0/5 * * * * *");
    }

}
