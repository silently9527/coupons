package cn.silently9527.coupons.rest.model.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 修改密码参数
 * @author starBlues
 * @version 1.0
 */
@Data
@ApiModel("修改当前用户密码")
public class UserUpdatePasswordParam {

    @ApiModelProperty(value = "旧密码", required = true)
    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;

    @ApiModelProperty(value = "新密码", required = true)
    @NotEmpty(message = "新密码不能为空")
    @Length(min = 6, message = "新密码长度不能小于6位")
    private String newPassword;

    @ApiModelProperty(value = "确认新密码", required = true)
    @NotEmpty(message = "确认新密码不能为空")
    private String confirmNewPassword;

}
