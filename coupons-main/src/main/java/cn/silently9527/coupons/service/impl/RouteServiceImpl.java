package cn.silently9527.coupons.service.impl;

import cn.silently9527.coupons.core.security.UserService;
import cn.silently9527.coupons.core.security.model.AuthUserInfo;
import cn.silently9527.coupons.repository.databases.entity.Route;
import cn.silently9527.coupons.repository.databases.entity.TabBar;
import cn.silently9527.coupons.repository.databases.mapper.RouteMapper;
import cn.silently9527.coupons.rest.model.param.route.RouteAddParam;
import cn.silently9527.coupons.rest.model.param.route.RouteUpdateParam;
import cn.silently9527.coupons.service.RouteService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户端的Route配置 服务实现类
 * </p>
 *
 * @author silently9527
 * @since 2021-10-16
 */
@Service
@AllArgsConstructor
public class RouteServiceImpl extends ServiceImpl<RouteMapper, Route> implements RouteService {
    private UserService userService;

    @Override
    public void addRoute(RouteAddParam param) {
        Route route = new Route();
        BeanUtils.copyProperties(param, route);

        AuthUserInfo authUserInfo = userService.getAuthUserInfo();
        route.setUserId(authUserInfo.getUserId());

        save(route);
    }

    @Override
    public void updateRoute(RouteUpdateParam param) {
        Route route = new Route();
        BeanUtils.copyProperties(param, route);

        AuthUserInfo authUserInfo = userService.getAuthUserInfo();
        route.setUserId(authUserInfo.getUserId());

        updateById(route);
    }

    @Override
    public void updateStatus(String routeId, Integer status) {
        Wrapper<Route> wrapper = Wrappers.<Route>lambdaQuery().eq(Route::getId, routeId);
        Route route = new Route();
        route.setStatus(status);
        update(route, wrapper);
    }

}
