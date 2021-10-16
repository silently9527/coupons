package cn.silently9527.coupons.rest.model.param.buttons;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("添加Buttons参数")
public class ButtonsAddParam {
    @ApiModelProperty(name = "page", value = "所属页面编码", required = true)
    @NotNull(message = "所属页面编码不能为空")
    private String page;

    @ApiModelProperty(name = "buttonCode", value = "按钮编码", required = true)
    @NotNull(message = "按钮编码不能为空")
    private String buttonCode;

    @ApiModelProperty(name = "text", value = "按钮文字")
    private String text;

    @ApiModelProperty(name = "iconType", value = "图标的类型：image、icon", required = true)
    @NotNull(message = "图标的类型不能为空")
    private String iconType;

    @ApiModelProperty(name = "image", value = "image")
    private String image;

    @ApiModelProperty(name = "url", value = "url", required = true)
    @NotNull(message = "url不能为空")
    private String url;

    @ApiModelProperty(name = "URL类型", value = "URL类型", required = true)
    @NotNull(message = "URL类型不能为空")
    private String urlType;

    @ApiModelProperty(name = "排序", value = "排序", required = true)
    @NotNull(message = "排序不能为空")
    private Integer sorted;

    @ApiModelProperty(name = "备注", value = "备注")
    private String remark;


}
