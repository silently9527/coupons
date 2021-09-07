package cn.silently9527.coupons.rest.model.param.user;

import cn.silently9527.coupons.rest.common.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户分页查询参数
 * @author starBlues
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户分页查询参数")
public class UserPageParam extends PageParam {

    @ApiModelProperty(name = "name", value = "姓名", required = false)
    private String name;

    @ApiModelProperty(name = "username", value = "用户名", required = false)
    private String username;

    @ApiModelProperty(name = "status", value = "状态(1: 启用、0: 禁用)", required = false)
    private Integer status;

    @ApiModelProperty(name = "locked", value = "是否被锁(true: 账户锁定、false: 账户未锁定)", required = false)
    private Boolean locked;

}
