package cn.silently9527.coupons.plugincenter;

import com.gitee.starblues.realize.BasePlugin;
import com.gitee.starblues.realize.OneselfListener;
import com.gitee.starblues.utils.OrderPriority;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PluginListener implements OneselfListener {

    @Override
    public OrderPriority order() {
        return OrderPriority.getMiddlePriority();
    }

    @Override
    public void startEvent(BasePlugin basePlugin) {
        log.info("initial plugin center data");

    }

    @Override
    public void stopEvent(BasePlugin basePlugin) {
        log.info("remove plugin center data");

    }
}
