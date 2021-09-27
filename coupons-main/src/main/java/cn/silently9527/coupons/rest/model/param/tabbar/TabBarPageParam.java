package cn.silently9527.coupons.rest.model.param.tabbar;

import cn.silently9527.coupons.rest.common.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("查询参数")
public class TabBarPageParam extends PageParam {

    private String tabBarId;


}
