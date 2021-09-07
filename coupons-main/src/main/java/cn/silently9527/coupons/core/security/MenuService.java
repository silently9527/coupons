package cn.silently9527.coupons.core.security;

import cn.silently9527.coupons.repository.databases.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.silently9527.coupons.rest.model.param.menu.MenuAddParam;
import cn.silently9527.coupons.rest.model.param.menu.MenuUpdatedParam;
import cn.silently9527.coupons.rest.security.model.vo.NavigationVo;
import cn.silently9527.coupons.core.security.model.MenuTree;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 菜单模型表 服务类
 * </p>
 *
 * @author starblues
 * @since 2020-12-31
 */
public interface MenuService extends IService<Menu> {

    /**
     * 得到当前认证的用户导航
     * @return 导航列表
     */
    NavigationVo getCurrentUserNav();

    /**
     * 获取全部的导航结合
     * @param haveDisable 是否包含禁用的导航
     * @return List 导航集合
     */
    List<Menu> getAllNav(boolean haveDisable);

    /**
     * 得到全部权限
     * @param haveDisable 是否包含禁用的导航
     * @return 全部权限
     */
    List<Menu> getAllPermission(boolean haveDisable);

    /**
     * 得到系统导航树
     * @param havePluginMenu 是否包含插件菜单
     * @return List
     */
    List<MenuTree> getMenuTree(Boolean havePluginMenu);

    /**
     * 根据当前登录的用户得到系统导航树
     * @param havePluginMenu 是否包含插件菜单
     * @return List
     * @throws Exception 获取异常
     */
    List<MenuTree> getMenuTreeByCurrentUser(Boolean havePluginMenu) throws Exception;

    /**
     * 修改状态
     * @param menuId 菜单id
     * @param status 1 启用, 0 禁用
     */
    void updateStatus(String menuId, int status);

    /**
     * 通过菜单id删除菜单
     * @param menuId 菜单id
     * @param isLogicDelete 是否逻辑删除
     */
    void removeMenuById(String menuId, boolean isLogicDelete);

    /**
     * 修改菜单
     * @param menu 修改的菜单bean
     */
    void updateMenu(MenuUpdatedParam menu);

    /**
     * 得到子菜单
     * @param menuId 菜单id
     * @return 子菜单集合
     */
    List<Menu> getChildMenu(String menuId);


    /**
     * 通过菜单id删除菜单
     * @param menus 菜单集合
     * @param isLogicDelete 是否逻辑删除
     */
    void removeMenuByIdsOfBean(Collection<Menu> menus, boolean isLogicDelete);

    /**
     * 添加菜单
     * @param menu 添加的菜单bean
     */
    void addMenu(MenuAddParam menu);
}
