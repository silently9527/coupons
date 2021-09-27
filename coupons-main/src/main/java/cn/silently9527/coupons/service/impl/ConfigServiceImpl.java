package cn.silently9527.coupons.service.impl;

import cn.silently9527.coupons.core.security.UserService;
import cn.silently9527.coupons.core.security.model.AuthUserInfo;
import cn.silently9527.coupons.repository.databases.entity.Config;
import cn.silently9527.coupons.repository.databases.mapper.ConfigMapper;
import cn.silently9527.coupons.service.ConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    private UserService userService;

    @Override
    public Config queryByConfigType(String configType) {
        AuthUserInfo authUserInfo = userService.getAuthUserInfo();
        LambdaQueryWrapper<Config> wrapper = Wrappers.<Config>lambdaQuery()
                .eq(Config::getConfigType, configType)
                .eq(Config::getUserId, authUserInfo.getUserId());
        Config config = getOne(wrapper);
        if (Objects.isNull(config)) {
            return null;
        }
        return config;
    }

    @Override
    public void update(String configType, String textJson) {
        AuthUserInfo authUserInfo = userService.getAuthUserInfo();
        LambdaQueryWrapper<Config> wrapper = Wrappers.<Config>lambdaQuery()
                .eq(Config::getConfigType, configType)
                .eq(Config::getUserId, authUserInfo.getUserId());

        Config config = getOne(wrapper);
        if (Objects.isNull(config)) {
            config = new Config();
        }
        config.setConfigType(configType);
        config.setUserId(authUserInfo.getUserId());
        config.setTextJson(textJson);
        saveOrUpdate(config);
    }

}
