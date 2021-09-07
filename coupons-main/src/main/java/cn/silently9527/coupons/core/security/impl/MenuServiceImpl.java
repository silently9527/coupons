package cn.silently9527.coupons.core.security.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.silently9527.coupons.config.PluginConfig;
import cn.silently9527.coupons.config.prop.SystemProp;
import cn.silently9527.coupons.core.exception.BusinessException;
import cn.silently9527.coupons.core.security.RoleMenuService;
import cn.silently9527.coupons.core.security.RoleService;
import cn.silently9527.coupons.core.security.UserService;
import cn.silently9527.coupons.repository.databases.entity.Menu;
import cn.silently9527.coupons.repository.databases.entity.User;
import cn.silently9527.coupons.repository.databases.mapper.MenuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.silently9527.coupons.rest.model.param.menu.MenuAddParam;
import cn.silently9527.coupons.rest.model.param.menu.MenuUpdatedParam;
import cn.silently9527.coupons.rest.security.model.vo.NavigationInfo;
import cn.silently9527.coupons.rest.security.model.vo.NavigationVo;
import cn.silently9527.coupons.core.security.MenuService;
import cn.silently9527.coupons.core.security.model.MenuTree;
import cn.silently9527.coupons.utils.AuthUtils;
import cn.silently9527.coupons.utils.HttpUtils;
import cn.silently9527.coupons.utils.ListToTree;
import com.gitee.starblues.integration.AutoIntegrationConfiguration;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.pf4j.RuntimeMode;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单模型表 服务实现类
 * </p>
 *
 * @author starblues
 * @since 2020-12-31
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    /**
     * 插件组件名称
     */
    private final static String PLUGIN_COMPONENT = "PluginExtensionWeb";

    /**
     * 父目录组件名称
     */
    private final static String PARENT_COMPONENT = "RouteView";

    private final SystemProp systemProp;
    private final RoleMenuService roleMenuService;
    private final AutoIntegrationConfiguration integrationConfiguration;
    private final UserService userService;
    private final RoleService roleService;

    @Override
    public NavigationVo getCurrentUserNav() {
        Set<String> roleCodes = AuthUtils.getCurrentRoleCodes();
        NavigationVo navigationVo = new NavigationVo();
        if(roleCodes.isEmpty()){
            return navigationVo;
        }
        List<Menu> menus = null;
        if(roleCodes.contains(systemProp.getSuperRoleCode())){
            menus = getAllNav(false);
        } else {
            menus = baseMapper.getNavByRoleCodes(roleCodes);
        }

        if(menus == null || menus.isEmpty()){
            return navigationVo;
        }
        List<NavigationInfo> navigationInfos = Lists.newArrayList();
        Map<String, NavigationVo.PluginWebInfo> pluginWebInfoMap = Maps.newHashMap();
        Map<String, Menu> notTopMenus = Maps.newHashMap();
        List<NavigationInfo> topNavigationInfo = Lists.newArrayListWithCapacity(4);
        String requestAddress = HttpUtils.getServiceAddress();

        RuntimeMode runtimeMode = RuntimeMode.byName(integrationConfiguration.getRunMode());

        for (Menu menu : menus) {
            if(menu == null){
                continue;
            }
            String parentId = menu.getParentId();
            NavigationInfo navigationInfo = getNavigationVo(menu);
            if(Objects.equals(parentId, "0")){
                topNavigationInfo.add(navigationInfo);
            } else {
                if(!notTopMenus.containsKey(parentId)){
                    notTopMenus.put(parentId, menu);
                }
            }
            if(Objects.equals(menu.getPluginMenu(), 1)){
                // 插件菜单
                if(runtimeMode == RuntimeMode.DEVELOPMENT){
                    // 开发环境下不处理插件菜单信息
                    continue;
                }
                String pluginId = menu.getPluginId();
                NavigationVo.PluginWebInfo pluginWebInfo = processPluginMenu(navigationInfo, menu, requestAddress);
                pluginWebInfoMap.put(pluginId, pluginWebInfo);
                navigationInfos.add(navigationInfo);
            } else {
                // 非插件菜单
                navigationInfos.add(navigationInfo);
            }
        }
        setTopRedirect(topNavigationInfo, notTopMenus);
        navigationVo.setNavigationInfos(navigationInfos);
        navigationVo.setPluginWebInfos(Lists.newArrayList(pluginWebInfoMap.values()));
        return navigationVo;
    }


    @Override
    public List<Menu> getAllNav(boolean haveDisable) {
        LambdaQueryWrapper<Menu> wrapper = Wrappers.<Menu>lambdaQuery()
                .eq(Menu::getDeleted, 0)
                .in(Menu::getType, 1, 2);
        if(!haveDisable){
            wrapper.eq(Menu::getEnable, 1);
        }
        wrapper.orderByAsc(Menu::getOrderNum);
        return list(wrapper);
    }

    @Override
    public List<Menu> getAllPermission(boolean haveDisable) {
        LambdaQueryWrapper<Menu> wrapper = Wrappers.<Menu>lambdaQuery()
                .eq(Menu::getDeleted, 0)
                .eq(Menu::getType, 3);
        if(!haveDisable){
            wrapper.eq(Menu::getEnable, 1);
        }
        return list(wrapper);
    }

    @Override
    public List<MenuTree> getMenuTree(Boolean havePluginMenu) {
        List<MenuTree> menus = baseMapper.getMenuTree(havePluginMenu);
        if(menus == null || menus.isEmpty()){
            return Collections.emptyList();
        }
        ListToTree<MenuTree> listToTree = getListToTree();
        return listToTree.listToTree(menus);
    }

    @Override
    public List<MenuTree> getMenuTreeByCurrentUser(Boolean havePluginMenu) throws Exception {
        String currentUsername = AuthUtils.getCurrentUsername();
        if(StrUtil.isEmpty(currentUsername)){
            throw new BusinessException("没有发现授权的用户");
        }
        User user = userService.getByUsername(currentUsername);
        if(Objects.equals(user.getUserId(), systemProp.getSuperAdminId())){
            // 为超级管理员
            return getMenuTree(havePluginMenu);
        }
        Set<String> roleCodes = AuthUtils.getCurrentRoleCodes();
        if(roleCodes.isEmpty()){
            return Collections.emptyList();
        }
        List<MenuTree> menuTree = super.baseMapper.getMenuTreeByRole(roleCodes, havePluginMenu);
        ListToTree<MenuTree> listToTree = getListToTree();
        return listToTree.listToTree(menuTree);
    }

    @Transactional
    @Override
    public void updateStatus(String menuId, int status) {
        Set<String> menuIds = Sets.newHashSet(menuId);
        resolveChildMenu(menuId, m->{
            menuIds.add(m.getMenuId());
        });
        Wrapper<Menu> wrapper = Wrappers.<Menu>lambdaQuery()
                .in(Menu::getMenuId, menuIds);
        Menu menu = new Menu();
        if(status == 1){
            menu.setEnable(1);
        } else {
            menu.setEnable(0);
        }
        update(menu, wrapper);
    }

    @Transactional
    @Override
    public void removeMenuById(String menuId, boolean isLogicDelete) {
        if(StrUtil.isEmpty(menuId)){
            return;
        }
        Set<String> menuIds = Sets.newHashSet(menuId);
        resolveChildMenu(menuId, m->{
            menuIds.add(m.getMenuId());
        });
        if(isLogicDelete){
            Wrapper<Menu> wrapper = Wrappers.<Menu>lambdaQuery()
                    .in(Menu::getMenuId, menuIds);
            Menu m = new Menu();
            m.setDeleted(1);
            update(m, wrapper);
        } else {
            removeByIds(menuIds);
            roleMenuService.removeByMenuId(menuIds);
        }
    }

    @Override
    public void updateMenu(MenuUpdatedParam param) {
        Menu updateBean = new Menu();
        BeanUtils.copyProperties(param, updateBean);
        updateById(updateBean);
    }

    @Transactional
    @Override
    public List<Menu> getChildMenu(String menuId) {
        if(StrUtil.isEmpty(menuId)){
            return Collections.emptyList();
        }
        Wrapper<Menu> wrapper = Wrappers.<Menu>lambdaQuery()
                .eq(Menu::getParentId, menuId);
        return list(wrapper);
    }

    private void resolveChildMenu(String parentMenuId, Consumer<Menu> consumer){
        List<Menu> childMenu = getChildMenu(parentMenuId);
        if(childMenu.isEmpty()){
            return;
        }
        for (Menu menu : childMenu) {
            if(menu == null){
                continue;
            }
            consumer.accept(menu);
            resolveChildMenu(menu.getMenuId(), consumer);
        }
    }


    @Override
    public void removeMenuByIdsOfBean(Collection<Menu> menus, boolean isLogicDelete) {
        if(menus == null || menus.isEmpty()){
            return;
        }
        if(isLogicDelete){
            Set<String> removeMenus = menus.stream()
                    .filter(menu -> menu != null)
                    .map(menu -> {
                        return menu.getMenuId();
                    }).collect(Collectors.toSet());
            if(removeMenus.isEmpty()){
                return;
            }
            Wrapper<Menu> wrapper = Wrappers.<Menu>lambdaQuery()
                    .in(Menu::getMenuId, removeMenus);
            Menu m = new Menu();
            m.setDeleted(1);
            update(m, wrapper);
        } else {
            Set<String> removeMenuIds = menus.stream()
                    .filter(menu -> menu != null)
                    .map(menu -> {
                        return menu.getMenuId();
                    }).collect(Collectors.toSet());
            if(removeMenuIds.isEmpty()){
                return;
            }
            removeByIds(removeMenuIds);
            roleMenuService.removeByMenuId(removeMenuIds);
        }
    }

    @Transactional
    @Override
    public void addMenu(MenuAddParam param) {
        Menu add = new Menu();
        BeanUtils.copyProperties(param, add);
        if(StrUtil.isEmpty(add.getParentId())){
            add.setParentId("0");
        }
        add.setDeleted(0);
        add.setEnable(1);
        add.setPluginMenu(0);
        save(add);
    }

    /**
     * 得到导航模型
     * @param menu 菜单
     * @return NavigationVo
     */
    private NavigationInfo getNavigationVo(Menu menu){
        NavigationInfo navigationInfo = new NavigationInfo();
        navigationInfo.setId(menu.getMenuId());
        navigationInfo.setParentId(menu.getParentId());
        navigationInfo.setKey(menu.getPermissions());
        navigationInfo.setComponent(menu.getComponent());
        navigationInfo.setPath(menu.getPath());
        NavigationInfo.Meta meta = new NavigationInfo.Meta();
        meta.setTitle(menu.getTitle());
        meta.setShow(true);
        meta.setIcon(menu.getIcon());
        if(Objects.equals(menu.getType(), 2)){
            // 菜单
            meta.setTarget(menu.getHtmlTarget());
        }
        navigationInfo.setMeta(meta);
        return navigationInfo;
    }

    /**
     * 设置顶级菜单的跳转路径
     * @param topNavigationInfo 顶级菜单列表
     * @param notTopMenus 非顶级菜单
     */
    private void setTopRedirect(List<NavigationInfo> topNavigationInfo, Map<String, Menu> notTopMenus) {
        if(topNavigationInfo.isEmpty()){
            return;
        }
        for (NavigationInfo navigationInfo : topNavigationInfo) {
            String firstRedirect = getFirstRedirect(navigationInfo.getId(), notTopMenus);
            if(StrUtil.isEmpty(firstRedirect)){
                continue;
            }
            navigationInfo.setRedirect(firstRedirect);
        }
    }

    /**
     * 递归获取第一个菜单型的路径
     * @param menuParentId 菜单的父id
     * @param notTopMenus 非顶级菜单
     * @return 菜单路径
     */
    private String getFirstRedirect(String menuParentId, Map<String, Menu> notTopMenus){
        Menu menu = notTopMenus.get(menuParentId);
        if(menu == null){
            return null;
        }
        if(Objects.equals(menu.getType(), 2)){
            return menu.getPath();
        } else {
            return getFirstRedirect(menu.getMenuId(), notTopMenus);
        }
    }

    /**
     * 处理插件菜单
     * @param navigationInfo 导航模型
     * @param menu 当前菜单
     * @param requestAddress 当前请求的地址
     */
    private NavigationVo.PluginWebInfo processPluginMenu(NavigationInfo navigationInfo, Menu menu, String requestAddress){
        navigationInfo.setPath(HttpUtils.pathJoin(menu.getPluginRootRouting(), menu.getPath()));
        navigationInfo.setComponent(PLUGIN_COMPONENT);

        NavigationVo.PluginWebInfo pluginWebInfo = new NavigationVo.PluginWebInfo();
        pluginWebInfo.setAppName(menu.getPluginAppName());
        String pluginAppPath = menu.getPluginAppPath();
        if(!HttpUtils.isHttpUrl(pluginAppPath)){
            pluginAppPath = requestAddress + HttpUtils.pathJoin(PluginConfig.STATIC_RESOURCE_PATH_PREFIX,
                    pluginAppPath);
        }
        pluginWebInfo.setAppPath(pluginAppPath);
        pluginWebInfo.setRootRouting(menu.getPluginRootRouting());
        return pluginWebInfo;
    }

    public static ListToTree<MenuTree> getListToTree(){
        return new ListToTree<MenuTree>(){

            @Override
            protected String getKey(MenuTree node) {
                return node.getMenuId();
            }

            @Override
            protected String getParentId(MenuTree node) {
                return node.getParentId();
            }

            @Override
            protected List<MenuTree> getChildrenList(MenuTree node) {
                return node.getChildren();
            }

            @Override
            protected void setChildrenList(MenuTree parentNode, List<MenuTree> childNodes) {
                parentNode.setChildren(childNodes);
            }
        };
    }

}
