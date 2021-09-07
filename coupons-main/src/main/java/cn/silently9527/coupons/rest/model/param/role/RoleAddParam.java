package cn.silently9527.coupons.rest.model.param.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 添加角色参数
 * @author starBlues
 * @version 1.0
 */
@Data
@ApiModel("添加角色参数")
public class RoleAddParam {

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
     * 帐号状态（1启用, 0停用）
     */
    @ApiModelProperty(name = "status", value = "帐号状态", required = true)
    @NotNull(message = "帐号状态不能为空")
    private Integer status;

    /**
     * 角色描述
     */
    @ApiModelProperty(name = "describe", value = "角色描述")
    private String describe;



}
