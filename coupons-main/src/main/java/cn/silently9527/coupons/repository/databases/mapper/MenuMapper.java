package cn.silently9527.coupons.repository.databases.mapper;

import cn.silently9527.coupons.repository.databases.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.silently9527.coupons.core.security.model.MenuTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单模型表 Mapper 接口
 * </p>
 *
 * @author starblues
 * @since 2020-12-31
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过角色id获取权限集合
     * @param roleIds 角色id集合
     * @param menuTypes 菜单类型集合
     * @return 菜单id集合
     */
    List<Menu> getMenuByRoleIds(@Param("roleIds") Set<String> roleIds,
                                @Param("menuTypes") Set<Integer> menuTypes);

    /**
     * 通过角色编号获取菜单导航集合
     * @param roleCodes 角色编号集合
     * @return 菜单导航集合
     */
    List<Menu> getNavByRoleCodes(@Param("roleCodes") Set<String> roleCodes);


    /**
     * 得到菜单树bean的集合
     * @param havePluginMenu 是否包含插件菜单
     * @return List
     */
    List<MenuTree> getMenuTree(@Param("havePluginMenu") Boolean havePluginMenu);

    /**
     * 根据角色编号得到菜单树bean的集合
     * @param roleCodes 角色编号
     * @param havePluginMenu 是否包含插件菜单
     * @return List
     */
    List<MenuTree> getMenuTreeByRole(@Param("roleCodes") Set<String> roleCodes,
                                     @Param("havePluginMenu") Boolean havePluginMenu);

}
