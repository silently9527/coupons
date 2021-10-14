package cn.silently9527.coupons.plugincenter.config;

import cn.hutool.core.lang.UUID;
import cn.silently9527.coupons.core.plugin.web.MenuDefine;
import cn.silently9527.coupons.core.plugin.web.WebViewRegister;
import cn.silently9527.coupons.plugin.web.PluginWebInterface;
import com.gitee.starblues.annotation.ConfigDefinition;
import com.gitee.starblues.extension.mybatis.mybatisplus.SpringBootMybatisPlusConfig;
import com.gitee.starblues.extension.resources.StaticResourceConfig;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;


@Data
@ConfigDefinition(fileName = "pluginCenter.yml", devSuffix = "-dev", prodSuffix = "-prod")
public class PluginCenterConfig implements SpringBootMybatisPlusConfig, StaticResourceConfig, PluginWebInterface {
    /**
     * 静态资源的路径
     */
    private Set<String> webResourceLocations;

    @Override
    public void config(WebViewRegister register) {
        register.setView("/index.html", "/plugin-center", "plugin-center")
                .addMenu(UUID.randomUUID().toString(true), new MenuDefine("200", MenuDefine.Type.MENU,
                        "插件中心管理", "/plugin-list").setIcon("pie-chart"));
    }


    @Override
    public Set<String> locations() {
        return getWebResourceLocations();
    }

    @Override
    public Set<String> entityPackage() {
        return Sets.newHashSet("cn.silently9527.coupons.plugincenter.repository.databases.entity");
    }

    @Override
    public Set<String> xmlLocationsMatch() {
        return Sets.newHashSet("classpath:/mapper/*.xml");
    }
}
