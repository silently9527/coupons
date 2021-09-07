package cn.silently9527.coupons.rest.security.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * 导航模型
 * @author starBlues
 * @version 1.0
 * @since 2020-12-31
 */
@Data
@ApiModel("导航模型")
public class NavigationVo {

    private List<NavigationInfo> navigationInfos;

    private List<PluginWebInfo> pluginWebInfos;

    @Data
    public static class PluginWebInfo{
        private String appName;
        private String appPath;
        private String rootRouting;
    }

}
