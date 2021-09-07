package cn.silently9527.coupons.rest.security.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 修改当前用户参数
 * @author starBlues
 * @version 1.0
 */
@Data
@ApiModel("修改当前用户信息")
public class CurrentUserInfoUpdatedParam {

    /**
     * 用户名称
     */
    @ApiModelProperty(name = "name", value = "用户名称", required = true)
    @NotEmpty(message = "用户名称不能为空")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty(name = "phone", value = "手机号")
    @NotEmpty(message = "手机号不能为空")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(name = "email", value = "邮箱")
    @NotEmpty(message = "邮箱不能为空")
    private String email;

}
