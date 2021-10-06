package cn.silently9527.coupons.plugincenter.rest.model;

import cn.silently9527.coupons.plugincenter.repository.databases.entity.Plugin;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.parameters.P;

@Data
public class PluginDetail {
    private String id;
    private String createUser;
    private String gmtCreated;
    private String modifiedUser;
    private String gmtModified;

    private String pluginName;
    private String icon;
    private String author;
    private String version;
    private String description;
    private int status; //1启用 0禁用

    private String downloadUrl;
    private String docUrl;

    private String price;

    private String qrcode;
    private String remark;

    public PluginDetail() {
    }

    public PluginDetail(Plugin plugin) {
        BeanUtils.copyProperties(plugin, this);
    }

    public static PluginDetail openAPIPluginDetail(Plugin plugin) {
        PluginDetail pluginDetail = new PluginDetail();
        BeanUtils.copyProperties(plugin, pluginDetail);
        pluginDetail.setDownloadUrl(null);
        return pluginDetail;
    }
}
