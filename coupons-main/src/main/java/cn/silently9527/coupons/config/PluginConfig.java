package cn.silently9527.coupons.config;

import com.gitee.starblues.extension.mybatis.SpringBootMybatisExtension;
import com.gitee.starblues.extension.resources.StaticResourceExtension;
import com.gitee.starblues.integration.AutoIntegrationConfiguration;
import com.gitee.starblues.integration.application.DefaultPluginApplication;
import com.gitee.starblues.integration.application.PluginApplication;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.CacheControl;

/**
 * 插件集成配置
 *
 * @author starBlues
 * @version 1.0
 */
@Configuration
@Data
@Import(AutoIntegrationConfiguration.class)
public class PluginConfig {

    /**
     * 插件静态资源路径访问前缀
     */
    public static final String STATIC_RESOURCE_PATH_PREFIX = "plugin-web";



    /**
     * 定义插件应用。使用可以注入它操作插件。
     * @return PluginApplication
     */
    @Bean
    public DefaultPluginApplication pluginApplication(){
        // 实例化自动初始化插件的PluginApplication
        DefaultPluginApplication pluginApplication = new DefaultPluginApplication();
        addStaticResourceExtension(pluginApplication);
        addMybatisPluginExtension(pluginApplication);
        return pluginApplication;
    }


    private void addStaticResourceExtension(PluginApplication pluginApplication){
        // 新增静态资源扩展
        StaticResourceExtension staticResourceExtension = new StaticResourceExtension();
        // 插件静态资源Http访问前缀
        staticResourceExtension.setPathPrefix(STATIC_RESOURCE_PATH_PREFIX);
        // 设置静态资源缓存策略
        staticResourceExtension.setCacheControl(CacheControl.noCache());
        pluginApplication.addExtension(staticResourceExtension);
    }

    private void addMybatisPluginExtension(PluginApplication pluginApplication){
        pluginApplication.addExtension(new SpringBootMybatisExtension(
                SpringBootMybatisExtension.Type.MYBATIS_PLUS));
    }

}
