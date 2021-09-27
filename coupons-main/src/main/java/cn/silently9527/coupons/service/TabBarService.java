package cn.silently9527.coupons.service;

import cn.silently9527.coupons.repository.databases.entity.TabBar;
import cn.silently9527.coupons.rest.model.param.tabbar.TabBarAddParam;
import cn.silently9527.coupons.rest.model.param.tabbar.TabBarUpdateParam;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.Valid;

/**
 * <p>
 * 客户端的tabBar配置 服务类
 * </p>
 *
 * @author silently9527
 * @since 2021-09-26
 */
public interface TabBarService extends IService<TabBar> {

    void addTabBar(TabBarAddParam param);

    void updateTabBar(TabBarUpdateParam param);

    void updateStatus(String tabBarId, Integer status);
}
