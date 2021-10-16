package cn.silently9527.coupons.service.impl;

import cn.silently9527.coupons.core.security.UserService;
import cn.silently9527.coupons.core.security.impl.UserServiceImpl;
import cn.silently9527.coupons.core.security.model.AuthUserInfo;
import cn.silently9527.coupons.repository.databases.entity.TabBar;
import cn.silently9527.coupons.repository.databases.entity.User;
import cn.silently9527.coupons.repository.databases.mapper.TabBarMapper;
import cn.silently9527.coupons.rest.model.param.tabbar.TabBarAddParam;
import cn.silently9527.coupons.rest.model.param.tabbar.TabBarUpdateParam;
import cn.silently9527.coupons.service.TabBarService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户端的tabBar配置 服务实现类
 * </p>
 *
 * @author silently9527
 * @since 2021-09-26
 */
@Service
@AllArgsConstructor
public class TabBarServiceImpl extends ServiceImpl<TabBarMapper, TabBar> implements TabBarService {

    private UserService userService;

    @Override
    public void addTabBar(TabBarAddParam param) {
        TabBar tabBar = new TabBar();
        BeanUtils.copyProperties(param, tabBar);

        AuthUserInfo authUserInfo = userService.getAuthUserInfo();
        tabBar.setUserId(authUserInfo.getUserId());

        save(tabBar);
    }

    @Override
    public void updateTabBar(TabBarUpdateParam param) {
        TabBar tabBar = new TabBar();
        BeanUtils.copyProperties(param, tabBar);

        AuthUserInfo authUserInfo = userService.getAuthUserInfo();
        tabBar.setUserId(authUserInfo.getUserId());

        updateById(tabBar);
    }

    @Override
    public void updateStatus(String tabBarId, Integer status) {
        Wrapper<TabBar> wrapper = Wrappers.<TabBar>lambdaQuery().eq(TabBar::getId, tabBarId);
        TabBar tabBar = new TabBar();
        tabBar.setStatus(status);
        update(tabBar, wrapper);
    }

}
