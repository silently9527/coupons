package cn.silently9527.coupons.rest.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class PluginDetail {
    private String id;

    private String pluginName;
    private String pluginCode; //英文

    private String icon;
    private String author;
    private String version;
    private String description;

    private String docUrl;
    private String price;

    private String qrcode;
    private String remark;

    private boolean installed;

    public PluginDetail(Object o) {
        JSONObject jb = (JSONObject) o;
        this.id = jb.getString("id");
        this.pluginName = jb.getString("pluginName");
        this.pluginCode = jb.getString("pluginCode");
        this.icon = jb.getString("icon");
        this.author = jb.getString("author");
        this.version = jb.getString("version");
        this.description = jb.getString("description");
        this.docUrl = jb.getString("docUrl");
        this.price = jb.getString("price");
        this.qrcode = jb.getString("qrcode");
        this.remark = jb.getString("remark");
    }
}
