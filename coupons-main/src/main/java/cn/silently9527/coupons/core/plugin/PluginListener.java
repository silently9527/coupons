package cn.silently9527.coupons.core.plugin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.silently9527.coupons.core.security.MenuService;
import cn.silently9527.coupons.repository.databases.entity.Menu;
import com.gitee.starblues.integration.application.PluginApplication;
import com.gitee.starblues.integration.listener.PluginInitializerListener;
import com.gitee.starblues.integration.operator.PluginOperator;
import com.gitee.starblues.integration.operator.module.PluginInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author starBlues
 * @version 1.0
 */
@Component
@AllArgsConstructor
public class PluginListener implements PluginInitializerListener {

    private final PluginApplication pluginApplication;
    private final MenuService menuService;


    @Override
    public void before() {

    }

    @Override
    public void complete() {
        // 插件全部初始化完成后, 进行菜单检查
        PluginOperator pluginOperator = pluginApplication.getPluginOperator();
        List<PluginInfo> pluginInfos = pluginOperator.getPluginInfo();
        Set<String> pluginIds = pluginInfos.stream()
                .map(pluginInfo -> pluginInfo.getPluginDescriptor().getPluginId())
                .collect(Collectors.toSet());
        LambdaQueryWrapper<Menu> wrapper = Wrappers.<Menu>lambdaQuery()
                .eq(Menu::getPluginMenu, 1);
        if(!pluginIds.isEmpty()){
            wrapper.notIn(Menu::getPluginId, pluginIds);
        }
        List<Menu> menus = menuService.list(wrapper);
        if(menus == null || menus.isEmpty()){
            return;
        }
        menuService.removeMenuByIdsOfBean(menus, true);
    }

    @Override
    public void failure(Throwable throwable) {

    }
}
