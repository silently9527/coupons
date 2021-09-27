package cn.silently9527.coupons.rest.model.param.tabbar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("添加TabBar参数")
public class TabBarAddParam {

    @ApiModelProperty(name = "pagePath", value = "页面路径", required = true)
    @NotNull(message = "页面路径不能为空")
    private String pagePath;

    @ApiModelProperty(name = "iconPath", value = "Tab图标", required = true)
    @NotNull(message = "Tab图标不能为空")
    private String iconPath;

    @ApiModelProperty(name = "selectedIconPath", value = "选中时Tab图标", required = true)
    @NotNull(message = "选中时Tab图标不能为空")
    private String selectedIconPath;

    @ApiModelProperty(name = "sorted", value = "Tab排序", required = true)
    @NotNull(message = "Tab排序不能为空")
    private int sorted;

    @ApiModelProperty(name = "text", value = "Tab名称")
    private String text;

    @ApiModelProperty(name = "remark", value = "备注")
    private String remark;

}
