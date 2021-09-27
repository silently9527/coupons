package cn.silently9527.coupons.service;

import cn.silently9527.coupons.repository.databases.entity.Config;
import cn.silently9527.coupons.repository.databases.entity.TabBar;
import cn.silently9527.coupons.rest.model.param.tabbar.TabBarAddParam;
import cn.silently9527.coupons.rest.model.param.tabbar.TabBarUpdateParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 * @author silently9527
 * @since 2021-09-26
 */
public interface ConfigService extends IService<Config> {
    Config queryByConfigType(String configType);

    void update(String configType, String textJson);
}
