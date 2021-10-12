package cn.silently9527.coupons.plugincenter.rest.params;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel("新增插件参数对象")
public class AddPluginParam {
    @NotEmpty(message = "插件code必填")
    private String pluginCode; //英文
    @NotEmpty(message = "插件名称必填")
    private String pluginName;
    private String icon;
    @NotEmpty(message = "作者必填")
    private String author;
    @NotEmpty(message = "版本必填")
    private String version;
    @NotEmpty(message = "描述必填")
    private String description;
    @NotEmpty(message = "下载地址必填")
    private String downloadUrl;
    @NotEmpty(message = "文档地址必填")
    private String docUrl;
    @NotEmpty(message = "价格必填")
    private String price;
    private String qrcode;
    private String remark;
}
