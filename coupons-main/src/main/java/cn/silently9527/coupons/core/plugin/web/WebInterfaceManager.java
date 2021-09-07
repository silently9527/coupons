package cn.silently9527.coupons.core.plugin.web;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.silently9527.coupons.core.security.MenuService;
import cn.silently9527.coupons.repository.databases.entity.Menu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * web 界面管理者
 * @author starBlues
 * @version 1.0
 */
@Component
@AllArgsConstructor
public class WebInterfaceManager {

    private final MenuService menuService;

    public void register(Menu menu){
        if(menu == null){
            return;
        }
        Menu databaseMenu = menuService.getById(menu.getMenuId());
        menu.setDeleted(0);
        if(databaseMenu == null){
            menuService.save(menu);
        } else {
            menuService.updateById(menu);
        }
    }

    /**
     * 卸载菜单
     * @param pluginId 插件id
     */
    @Transactional
    public void unRegister(String pluginId){
        if(StrUtil.isEmpty(pluginId)){
            return;
        }
        Wrapper<Menu> wrapper = Wrappers.<Menu>lambdaQuery()
                .eq(Menu::getPluginId, pluginId);
        List<Menu> pluginMenus = menuService.list(wrapper);
        if(pluginMenus == null || pluginMenus.isEmpty()){
            return;
        }
        menuService.removeMenuByIdsOfBean(pluginMenus, true);
    }


}
