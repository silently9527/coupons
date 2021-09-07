package cn.silently9527.coupons.rest.security.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 角色集合参数
 * @author starBlues
 * @version 1.0
 */
@Data
@ApiModel("角色集合参数")
public class RoleSetParam {

    @ApiModelProperty(name = "roleCodes", value = "角色编号集合", required = true)
    @NotNull(message = "角色集合不能为空")
    private Set<String> roleCodes;

}
