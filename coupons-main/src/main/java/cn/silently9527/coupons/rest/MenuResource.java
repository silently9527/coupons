package cn.silently9527.coupons.rest;

import cn.silently9527.coupons.rest.common.BaseResource;
import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import cn.silently9527.coupons.core.security.MenuService;
import cn.silently9527.coupons.rest.model.param.menu.MenuAddParam;
import cn.silently9527.coupons.rest.model.param.menu.MenuUpdatedParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import static cn.silently9527.coupons.rest.common.Result.*;
import static cn.silently9527.coupons.rest.common.ResultUtils.errorLog;

/**
 * 菜单接口
 * @author starBlues
 * @version 1.0
 */
@RestController
@RequestMapping(BaseResource.API + "menu")
@Api(tags = "菜单接口")
@AllArgsConstructor
@Slf4j
public class MenuResource extends BaseResource {

    private final MenuService menuService;

    @PostMapping
    @PreAuthorize("@auth.permission('menu:add')")
    @ApiOperation("添加菜单")
    public Result<String> add(@RequestBody @Valid MenuAddParam param){
        try {
            menuService.addMenu(param);
            return success(ApiEnum.ADD_SUCCESS, "添加菜单成功");
        } catch (Exception e) {
            errorLog(log, e, "添加菜单 '{}' 信息失败 {}", param.getTitle(), e.getMessage());
            return failure(ApiEnum.ADD_ERROR, "添加菜单失败.", e);
        }
    }

    @PutMapping
    @PreAuthorize("@auth.permission('menu:update')")
    @ApiOperation("修改菜单")
    public Result<String> update(@RequestBody @Valid MenuUpdatedParam param){
        try {
            menuService.updateMenu(param);
            return success(ApiEnum.UPDATE_SUCCESS, "修改菜单成功");
        } catch (Exception e) {
            errorLog(log, e, "更新菜单 '{}' 信息失败 {}", param.getMenuId(), e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, "修改菜单失败.", e);
        }
    }

    @PutMapping("{menuId}/{enable}")
    @PreAuthorize("@auth.permission('menu:update')")
    @ApiOperation("修改菜单状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单id", paramType = "path", required = true),
            @ApiImplicitParam(name = "status", value = "状态（1启用, 0停用）", paramType = "path", required = true)
    })
    public Result<String> updateStatus(@PathVariable("menuId") String menuId,
                                       @PathVariable("enable") Integer enable){
        String message = enable == 1 ? "启用" : "禁用";
        try {
            menuService.updateStatus(menuId, enable);
            return response(ApiEnum.UPDATE_SUCCESS, message + "成功");
        } catch (Exception e) {
            errorLog(log, e, "修改菜单状态失败. {}", e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, message + "失败", e);
        }
    }


    @DeleteMapping("/{menuId}")
    @PreAuthorize("@auth.permission('menu:delete')")
    @ApiOperation("删除菜单")
    @ApiImplicitParam(name = "menuId", value = "菜单id", paramType = "path", required = true)
    public Result<String> delete(@PathVariable("menuId") String menuId){
        try {
            menuService.removeMenuById(menuId, false);
            return response(ApiEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            errorLog(log, e, "删除菜单失败. {}", e.getMessage());
            return failure(ApiEnum.DELETE_ERROR, "删除失败", e);
        }
    }


}
