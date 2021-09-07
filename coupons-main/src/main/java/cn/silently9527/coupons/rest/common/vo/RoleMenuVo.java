package cn.silently9527.coupons.rest.common.vo;

import cn.silently9527.coupons.core.security.model.MenuTree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 角色权限模型
 * @author starBlues
 * @version 1.0
 */
@ApiModel("角色权限模型")
@Data
public class RoleMenuVo {

    @ApiModelProperty("当前角色已经分配的菜单id")
    private Set<String> menuIds;

    @ApiModelProperty("当前登录的用户所拥有的菜单树")
    private List<MenuTree> menuTree;

}
