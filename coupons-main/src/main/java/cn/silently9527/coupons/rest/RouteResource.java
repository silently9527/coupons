package cn.silently9527.coupons.rest;


import cn.silently9527.coupons.repository.databases.entity.Route;
import cn.silently9527.coupons.repository.databases.entity.TabBar;
import cn.silently9527.coupons.rest.common.BaseResource;
import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import cn.silently9527.coupons.rest.model.param.route.RouteAddParam;
import cn.silently9527.coupons.rest.model.param.route.RoutePageParam;
import cn.silently9527.coupons.rest.model.param.route.RouteUpdateParam;
import cn.silently9527.coupons.rest.model.param.tabbar.TabBarAddParam;
import cn.silently9527.coupons.rest.model.param.tabbar.TabBarPageParam;
import cn.silently9527.coupons.rest.model.param.tabbar.TabBarUpdateParam;
import cn.silently9527.coupons.service.RouteService;
import cn.silently9527.coupons.service.TabBarService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

import static cn.silently9527.coupons.rest.common.Result.*;
import static cn.silently9527.coupons.rest.common.Result.failure;
import static cn.silently9527.coupons.rest.common.ResultUtils.errorLog;

/**
 * <p>
 * 客户端的Route配置 前端控制器
 * </p>
 *
 * @author silently9527
 * @since 2021-10-16
 */
@Slf4j
@RestController
@RequestMapping(BaseResource.API + "route")
@Api(tags = "Route接口")
@AllArgsConstructor
public class RouteResource {
    private RouteService routeService;

    @GetMapping()
    @ApiOperation("分页查询")
    public Result<IPage<Route>> pageQuery(@Valid RoutePageParam param) {
        LambdaQueryWrapper<Route> wrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(param.getRouteId())) {
            wrapper.eq(Route::getId, param.getRouteId());
        }
        Page<Route> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        page = routeService.page(page, wrapper);
        return success(ApiEnum.GET_SUCCESS, page);
    }

    @PostMapping
    @ApiOperation("添加Tab")
    public Result<String> add(@RequestBody @Valid RouteAddParam param) {
        try {
            routeService.addRoute(param);
            return success(ApiEnum.ADD_SUCCESS, "添加成功");
        } catch (Exception e) {
            errorLog(log, e, "添加 '{}' 信息失败 {}", param.getPath(), e.getMessage());
            return failure(ApiEnum.ADD_ERROR, "添加失败.", e);
        }
    }

    @PutMapping
    @ApiOperation("修改Tab")
    public Result<String> update(@RequestBody @Valid RouteUpdateParam param) {
        try {
            routeService.updateRoute(param);
            return success(ApiEnum.ADD_SUCCESS, "添加成功");
        } catch (Exception e) {
            errorLog(log, e, "添加 '{}' 信息失败 {}", param.getPath(), e.getMessage());
            return failure(ApiEnum.ADD_ERROR, "添加失败.", e);
        }
    }

    @PutMapping("{routeId}/{status}")
    @ApiOperation("修改TabBar状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "routeId", value = "routeId", paramType = "path", required = true),
            @ApiImplicitParam(name = "status", value = "状态（1启用, 0停用）", paramType = "path", required = true)
    })
    public Result<String> updateStatus(@PathVariable("routeId") String routeId,
                                       @PathVariable("status") Integer status) {
        String message = status == 1 ? "启用" : "禁用";
        try {
            routeService.updateStatus(routeId, status);
            return response(ApiEnum.UPDATE_SUCCESS, message + "成功");
        } catch (Exception e) {
            errorLog(log, e, "修改状态失败. {}", e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, message + "失败", e);
        }
    }


    @DeleteMapping("/{routeId}")
    @ApiOperation("删除Route")
    @ApiImplicitParam(name = "routeId", value = "routeId", paramType = "path", required = true)
    public Result<String> delete(@PathVariable("routeId") String routeId) {
        try {
            routeService.removeById(routeId);
            return response(ApiEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            errorLog(log, e, "删除失败. {}", e.getMessage());
            return failure(ApiEnum.DELETE_ERROR, "删除失败", e);
        }
    }

}

