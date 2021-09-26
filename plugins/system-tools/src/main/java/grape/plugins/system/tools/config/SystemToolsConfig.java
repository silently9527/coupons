package grape.plugins.system.tools.config;

import cn.silently9527.coupons.core.plugin.web.MenuDefine;
import cn.silently9527.coupons.core.plugin.web.WebViewRegister;
import cn.silently9527.coupons.plugin.web.PluginWebInterface;
import com.gitee.starblues.annotation.ConfigDefinition;
import com.gitee.starblues.extension.mybatis.mybatisplus.SpringBootMybatisPlusConfig;
import com.gitee.starblues.extension.resources.StaticResourceConfig;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

/**
 * @author starBlues
 * @version 1.0
 */
@Data
@ConfigDefinition(fileName = "system-tools.yml", devSuffix = "-dev", prodSuffix = "-prod")
public class SystemToolsConfig implements SpringBootMybatisPlusConfig, StaticResourceConfig, PluginWebInterface {

    /**
     * 静态资源的路径
     */
    private Set<String> webResourceLocations;

    /**
     * 代码生成界面配置路径
     */
    private String codeGeneratorWebPath;

    /**
     * 代码生成存储目录
     */
    private String codeGeneratorDir;


    @Override
    public Set<String> entityPackage() {
        return Sets.newHashSet("blocks.plugins.system.tools.entity");
    }

    @Override
    public Set<String> xmlLocationsMatch() {
        return Sets.newHashSet("classpath:/mapper/*.xml");
    }

    @Override
    public void config(WebViewRegister register) {
        register.setView("/index.html", "/system-tools", "system-tools")
                .addMenu("code-generator", new MenuDefine("200", MenuDefine.Type.MENU,
                        "代码生成", "/code-generator").setIcon("code"));
    }


    @Override
    public Set<String> locations() {
        return getWebResourceLocations();
    }

}
