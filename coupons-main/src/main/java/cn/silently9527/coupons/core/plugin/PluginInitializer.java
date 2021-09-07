package cn.silently9527.coupons.core.plugin;

import cn.silently9527.coupons.core.plugin.web.WebInterfacePluginListener;
import com.gitee.starblues.integration.application.DefaultPluginApplication;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 插件初始化这
 * @author starBlues
 * @version 1.0
 */
@Component
@AllArgsConstructor
public class PluginInitializer implements InitializingBean {

    private final ApplicationContext applicationContext;
    private final DefaultPluginApplication defaultPluginApplication;
    private final PluginListener pluginListener;
    private final WebInterfacePluginListener webInterfacePluginListener;

    @Override
    public void afterPropertiesSet() throws Exception {
        defaultPluginApplication.addListener(webInterfacePluginListener);
        defaultPluginApplication.initialize(applicationContext, pluginListener);
    }
}
