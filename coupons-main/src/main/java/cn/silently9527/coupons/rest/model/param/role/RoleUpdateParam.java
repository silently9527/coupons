package cn.silently9527.coupons.rest.model.param.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 修改角色参数
 * @author starBlues
 * @version 1.0
 */
@Data
@ApiModel("修改角色参数")
public class RoleUpdateParam {


    /**
     * 角色名称
     */
    @ApiModelProperty(name = "roleId", value = "角色id", required = true)
    @NotEmpty(message = "角色id不能为空")
    private String roleId;

    /**
     * 角色名称
     */
    @ApiModelProperty(name = "name", value = "角色名称", required = true)
    @NotEmpty(message = "角色名称不能为空")
    private String name;

    /**
     * 角色编码
     */
    @ApiModelProperty(name = "code", value = "角色编码", required = true)
    @NotEmpty(message = "角色编码不能为空")
    private String code;

    /**
     * 角色描述
     */
    @ApiModelProperty(name = "description", value = "角色描述")
    private String description;


}
