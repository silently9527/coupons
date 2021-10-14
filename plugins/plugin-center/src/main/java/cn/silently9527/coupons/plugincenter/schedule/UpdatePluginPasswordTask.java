package cn.silently9527.coupons.plugincenter.schedule;

import cn.silently9527.coupons.core.schedule.ScheduledTaskRegistrar;
import cn.silently9527.coupons.plugincenter.service.PluginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class UpdatePluginPasswordTask {
    private static final String taskName = "UpdatePluginPasswordTask";
    private static final String cron = "0 0 0/2 * * *";

    private ScheduledTaskRegistrar scheduledTaskRegistrar;
    private PluginService pluginService;


    @PostConstruct
    public void init() {
        scheduledTaskRegistrar.startCron(taskName, () -> pluginService.resetPassword(), cron);
    }

}
