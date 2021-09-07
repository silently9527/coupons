package cn.silently9527.coupons.core.security;

import cn.silently9527.coupons.repository.databases.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;


/**
 * <p>
 * 角色菜单权限表 服务类
 * </p>
 *
 * @author starblues
 * @since 2020-12-31
 */
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 通过菜单id删除
     * @param menuId 菜单id
     */
    void removeByMenuId(String menuId);

    /**
     * 通过菜单id删除
     * @param menuIds 菜单id集合
     */
    void removeByMenuId(Collection<String> menuIds);


    /**
     * 角色id
     * @param roleId 角色id
     */
    void removeByRoleId(String roleId);

}
