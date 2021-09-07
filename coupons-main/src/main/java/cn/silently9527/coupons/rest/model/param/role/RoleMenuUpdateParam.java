package cn.silently9527.coupons.rest.model.param.role;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * 添加角色权限参数
 * @author starBlues
 * @version 1.0
 */
@Data
@ApiModel("添加角色权限参数")
public class RoleMenuUpdateParam {
    /**
     * 选中菜单id
     */
    @ApiModelProperty(name = "menus", value = "选中菜单id")
    private Set<String> menus;
    /**
     * 未选中父菜单id
     */
    @ApiModelProperty(name = "parentMenus", value = "未选中父菜单id")
    private Set<String> parentMenus;
    /**
     * 角色id
     */
    @ApiModelProperty(name = "roleId", value = "角色id", required = true)
    @NotEmpty(message = "角色id不能为空")
    private String roleId;
}
