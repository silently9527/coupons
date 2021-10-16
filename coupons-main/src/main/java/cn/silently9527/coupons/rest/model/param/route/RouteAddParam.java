package cn.silently9527.coupons.rest.model.param.route;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("添加Route参数")
public class RouteAddParam {

    @ApiModelProperty(name = "path", value = "页面路径", required = true)
    @NotNull(message = "页面路径不能为空")
    private String path;

    @ApiModelProperty(name = "style", value = "样式")
    private String style;


}
