package cn.silently9527.coupons.rest.model.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * 重置密码参数
 * @author starBlues
 * @version 1.0
 */
@Data
@ApiModel("重置用户密码参数")
public class UserResetPasswordParam {


    @ApiModelProperty(value = "用户id", required = true)
    @NotEmpty(message = "userId 不能为空")
    private String userId;

    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, message = "密码长度不能小于6位")
    private String password;


}
