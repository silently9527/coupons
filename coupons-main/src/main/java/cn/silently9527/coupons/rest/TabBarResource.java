package cn.silently9527.coupons.rest;


import cn.hutool.core.util.StrUtil;
import cn.silently9527.coupons.repository.databases.entity.OauthClientDetails;
import cn.silently9527.coupons.repository.databases.entity.TabBar;
import cn.silently9527.coupons.rest.common.BaseResource;
import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import cn.silently9527.coupons.rest.common.param.PageParam;
import cn.silently9527.coupons.rest.model.param.menu.MenuAddParam;
import cn.silently9527.coupons.rest.model.param.oauthclient.OauthClientPageParam;
import cn.silently9527.coupons.rest.model.param.tabbar.TabBarAddParam;
import cn.silently9527.coupons.rest.model.param.tabbar.TabBarPageParam;
import cn.silently9527.coupons.rest.model.param.tabbar.TabBarUpdateParam;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Objects;

import static cn.silently9527.coupons.rest.common.Result.*;
import static cn.silently9527.coupons.rest.common.ResultUtils.errorLog;

/**
 * <p>
 * 客户端的tabBar配置 前端控制器
 * </p>
 *
 * @author silently9527
 * @since 2021-09-26
 */
@Slf4j
@RestController
@RequestMapping(BaseResource.API + "tabbar")
@Api(tags = "TabBar接口")
@AllArgsConstructor
public class TabBarResource {

    private TabBarService tabBarService;

    @GetMapping()
    @ApiOperation("分页查询")
    public Result<IPage<TabBar>> pageQuery(@Valid TabBarPageParam param) {
        LambdaQueryWrapper<TabBar> wrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(param.getTabBarId())) {
            wrapper.eq(TabBar::getId, param.getTabBarId());
        }
        wrapper.orderByDesc(TabBar::getSorted);
        Page<TabBar> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        page = tabBarService.page(page, wrapper);
        return success(ApiEnum.GET_SUCCESS, page);
    }

    @PostMapping
    @ApiOperation("添加Tab")
    public Result<String> add(@RequestBody @Valid TabBarAddParam param) {
        try {
            tabBarService.addTabBar(param);
            return success(ApiEnum.ADD_SUCCESS, "添加成功");
        } catch (Exception e) {
            errorLog(log, e, "添加 '{}' 信息失败 {}", param.getPagePath(), e.getMessage());
            return failure(ApiEnum.ADD_ERROR, "添加失败.", e);
        }
    }

    @PutMapping
    @ApiOperation("修改Tab")
    public Result<String> update(@RequestBody @Valid TabBarUpdateParam param) {
        try {
            tabBarService.updateTabBar(param);
            return success(ApiEnum.ADD_SUCCESS, "添加成功");
        } catch (Exception e) {
            errorLog(log, e, "添加 '{}' 信息失败 {}", param.getPagePath(), e.getMessage());
            return failure(ApiEnum.ADD_ERROR, "添加失败.", e);
        }
    }

    @PutMapping("{tabBarId}/{status}")
    @ApiOperation("修改TabBar状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tabBarId", value = "tabBarId", paramType = "path", required = true),
            @ApiImplicitParam(name = "status", value = "状态（1启用, 0停用）", paramType = "path", required = true)
    })
    public Result<String> updateStatus(@PathVariable("tabBarId") String tabBarId,
                                       @PathVariable("status") Integer status) {
        String message = status == 1 ? "启用" : "禁用";
        try {
            tabBarService.updateStatus(tabBarId, status);
            return response(ApiEnum.UPDATE_SUCCESS, message + "成功");
        } catch (Exception e) {
            errorLog(log, e, "修改状态失败. {}", e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, message + "失败", e);
        }
    }


    @DeleteMapping("/{tabBarId}")
    @ApiOperation("删除TabBar")
    @ApiImplicitParam(name = "tabBarId", value = "tabBarId", paramType = "path", required = true)
    public Result<String> delete(@PathVariable("tabBarId") String tabBarId) {
        try {
            tabBarService.removeById(tabBarId);
            return response(ApiEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            errorLog(log, e, "删除菜单失败. {}", e.getMessage());
            return failure(ApiEnum.DELETE_ERROR, "删除失败", e);
        }
    }


}

