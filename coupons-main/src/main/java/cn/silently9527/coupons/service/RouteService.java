package cn.silently9527.coupons.service;

import cn.silently9527.coupons.repository.databases.entity.Route;
import cn.silently9527.coupons.rest.model.param.route.RouteAddParam;
import cn.silently9527.coupons.rest.model.param.route.RouteUpdateParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 客户端的Route配置 服务类
 * </p>
 *
 * @author silently9527
 * @since 2021-10-16
 */
public interface RouteService extends IService<Route> {

    void addRoute(RouteAddParam param);

    void updateRoute(RouteUpdateParam param);

    void updateStatus(String routeId, Integer status);
}
