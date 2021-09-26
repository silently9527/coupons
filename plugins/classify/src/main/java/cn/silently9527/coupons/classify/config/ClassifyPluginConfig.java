package cn.silently9527.coupons.classify.config;

import cn.silently9527.coupons.core.plugin.web.WebViewRegister;
import cn.silently9527.coupons.plugin.web.PluginWebInterface;
import com.gitee.starblues.annotation.ConfigDefinition;
import com.gitee.starblues.extension.resources.StaticResourceConfig;
import lombok.Data;

import java.util.Set;

/**
 * @author starBlues
 * @version 1.0
 */
@Data
@ConfigDefinition(fileName = "classify.yml", devSuffix = "-dev", prodSuffix = "-prod")
public class ClassifyPluginConfig implements StaticResourceConfig, PluginWebInterface {

    @Override
    public void config(WebViewRegister register) {
    }


    @Override
    public Set<String> locations() {
        return null;
    }

}
