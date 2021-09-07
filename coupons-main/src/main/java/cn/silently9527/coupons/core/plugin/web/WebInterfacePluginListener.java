package cn.silently9527.coupons.core.plugin.web;

import cn.hutool.core.util.StrUtil;
import cn.silently9527.coupons.core.security.UserService;
import cn.silently9527.coupons.plugin.web.PluginWebInterface;
import cn.silently9527.coupons.repository.databases.entity.Menu;
import cn.silently9527.coupons.repository.databases.entity.User;
import cn.silently9527.coupons.utils.HttpUtils;
import com.gitee.starblues.integration.application.PluginApplication;
import com.gitee.starblues.integration.listener.PluginListener;
import com.gitee.starblues.integration.user.PluginUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 插件监听者
 * @author starBlues
 * @version 1.0
 */
@Component
@AllArgsConstructor
@Slf4j
public class WebInterfacePluginListener implements PluginListener {

    private final WebInterfaceManager webInterfaceManager;
    private final PluginApplication pluginApplication;
    private final UserService userService;
    private final ApplicationContext applicationContext;


    @Override
    public void registry(String pluginId, boolean isStartInitial) {
        PluginUser pluginUser = pluginApplication.getPluginUser();
        List<PluginWebInterface> pluginWebInterfaces = pluginUser.getPluginBeans(pluginId, PluginWebInterface.class);
        if(pluginWebInterfaces == null || pluginWebInterfaces.isEmpty()){
            return;
        }
        String superAdmin = getSuperAdmin();
        for (PluginWebInterface pluginWebInterface : pluginWebInterfaces) {
            WebMenuInfo webMenuInfo = new WebMenuInfo();
            WebViewRegister register = new WebViewRegister(webMenuInfo);
            pluginWebInterface.config(register);
            Map<String, MenuDefine> menuDefineMap = webMenuInfo.getMenuDefineMap();
            if(menuDefineMap.isEmpty()){
                continue;
            }
            String appPath = getPath(pluginId, register.getAppPath());
            String appName = register.getAppName();
            if(StrUtil.isEmpty(appName)){
                appName = pluginId;
            }
            for (String id : menuDefineMap.keySet()) {
                MenuDefine menuDefine = menuDefineMap.get(id);
                try {
                    Menu menu = getMenu(id, superAdmin, menuDefine);
                    menu.setPluginId(pluginId);
                    menu.setPluginAppName(appName);
                    menu.setPluginAppPath(appPath);
                    menu.setPluginRootRouting(register.getRootRouting());
                    webInterfaceManager.register(menu);
                } catch (Exception e){
                    log.error("注册插件 '{}' 菜单 {} 失败.", pluginId, id, e);
                }
            }
        }
    }



    @Override
    public void unRegistry(String pluginId) {
        try {
            webInterfaceManager.unRegister(pluginId);
        } catch (Exception e){
            log.error("卸载插件 '{}' 菜单失败.", pluginId, e);
        }
    }

    @Override
    public void failure(String pluginId, Throwable throwable) {

    }

    private Menu getMenu(String id, String superAdmin, MenuDefine define){
        Menu menu = new Menu();
        BeanUtils.copyProperties(define, menu);
        menu.setMenuId(id);
        menu.setPermissions(id);
        menu.setCreateUser(superAdmin);
        menu.setModifiedUser(superAdmin);
        menu.setPluginMenu(1);

        if(define.getType() == MenuDefine.Type.DIRECTORY){
            menu.setType(1);
        } else {
            menu.setType(2);
        }
        if(define.getEnable() == null){
            menu.setEnable(1);
        } else {
            menu.setEnable(define.getEnable() ? 1 : 0);
        }
        if(define.getOrderNum() == null){
            menu.setOrderNum(Integer.MAX_VALUE);
        }
        return menu;
    }


    private String getSuperAdmin(){
        User superAdmin = userService.getSuperAdmin();
        if(superAdmin != null){
            return superAdmin.getUsername();
        } else {
            return "NOT_FOUND";
        }
    }

    private String getPath(String pluginId, String path){
        // 设置menu菜单的path
        if(HttpUtils.isHttpUrl(path)){
            // 如果菜单地址为http url, 则直接返回
            return path;
        }
        return HttpUtils.pathJoin(pluginId + path);
    }

}
