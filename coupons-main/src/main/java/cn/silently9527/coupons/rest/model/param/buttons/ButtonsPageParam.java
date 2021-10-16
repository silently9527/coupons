package cn.silently9527.coupons.rest.model.param.buttons;

import cn.silently9527.coupons.rest.common.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("查询参数")
public class ButtonsPageParam extends PageParam {
    private String page;
    private String buttonCode;
}
