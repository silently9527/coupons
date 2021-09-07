package cn.silently9527.coupons.rest;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.silently9527.coupons.config.prop.SystemProp;
import cn.silently9527.coupons.core.security.MenuService;
import cn.silently9527.coupons.core.security.RoleMenuService;
import cn.silently9527.coupons.core.security.RoleService;
import cn.silently9527.coupons.core.security.model.MenuTree;
import cn.silently9527.coupons.repository.databases.entity.Role;
import cn.silently9527.coupons.repository.databases.entity.RoleMenu;
import cn.silently9527.coupons.rest.common.BaseResource;
import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import cn.silently9527.coupons.rest.common.vo.RoleMenuVo;
import cn.silently9527.coupons.rest.model.param.role.RoleAddParam;
import cn.silently9527.coupons.rest.model.param.role.RoleMenuUpdateParam;
import cn.silently9527.coupons.rest.model.param.role.RolePageParam;
import cn.silently9527.coupons.rest.model.param.role.RoleUpdateParam;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static cn.silently9527.coupons.rest.common.Result.*;
import static cn.silently9527.coupons.rest.common.ResultUtils.errorLog;

/**
 * 角色接口
 * @author starBlues
 * @version 1.0
 */
@RestController
@RequestMapping(BaseResource.API + "role")
@Api(tags = "角色接口")
@AllArgsConstructor
@Slf4j
public class RoleResource extends BaseResource {


    private final RoleService roleService;
    private final SystemProp systemProp;
    private final RoleMenuService roleMenuService;
    private final MenuService menuService;

    @GetMapping()
    @PreAuthorize("@auth.permission('role:query')")
    @ApiOperation("分页条件查询角色")
    public Result<IPage<Role>> pageQuery(@Valid RolePageParam param) {
        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery()
                .ne(Role::getCode, systemProp.getSuperRoleCode());
        if(!StrUtil.isEmpty(param.getName())){
            wrapper.like(Role::getName, param.getName());
        }
        if(!StrUtil.isEmpty(param.getRoleCode())){
            wrapper.like(Role::getCode, param.getRoleCode());
        }
        Integer status = param.getStatus();
        if(status != null && (status == 1 || status == 0)){
            wrapper.eq(Role::getStatus, param.getStatus());
        }
        wrapper.eq(Role::getDeleted, 0)
                .orderByDesc(Role::getGmtCreated);
        Page<Role> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        page = roleService.page(page, wrapper);
        return success(ApiEnum.GET_SUCCESS, page);
    }

    @GetMapping("/role-menu")
    @PreAuthorize("@auth.permission('role:setMenu')")
    @ApiOperation("分配角色时, 查询当前角色的菜单和权限")
    public Result<RoleMenuVo> getRoleMenu(@RequestParam("roleId") String roleId) {
        try {
            RoleMenuVo roleMenuVo = new RoleMenuVo();
            List<MenuTree> menuTreeByCurrentUser = menuService.getMenuTreeByCurrentUser(true);

            LambdaQueryWrapper<RoleMenu> wrapper = Wrappers.<RoleMenu>lambdaQuery()
                    .eq(RoleMenu::getRoleId, roleId)
                    .eq(RoleMenu::getIsParent, 0);
            List<RoleMenu> roleMenus = roleMenuService.list(wrapper);
            Set<String> menuIds = Sets.newHashSet();
            for (RoleMenu roleMenu : roleMenus) {
                menuIds.add(roleMenu.getMenuId());
            }
            roleMenuVo.setMenuIds(menuIds);
            roleMenuVo.setMenuTree(menuTreeByCurrentUser);
            return success(ApiEnum.GET_SUCCESS, roleMenuVo);
        } catch (Exception e) {
            errorLog(log, e, "获取角色 '{}' 权限菜单信息失败", roleId);
            return failure(ApiEnum.GET_SUCCESS, e);
        }
    }

    @PutMapping("/role-menu")
    @PreAuthorize("@auth.permission('role:setMenu')")
    @ApiOperation("分配角色菜单和权限")
    public Result<String> updateRoleMenu(@RequestBody @Valid RoleMenuUpdateParam param){
        try {
            roleService.updateRoleMenu(param);
            return response(ApiEnum.UPDATE_SUCCESS, "修改角色权限成功");
        } catch (Exception e) {
            errorLog(log, e, "更新角色权限 '{}' 失败 {}", param.getRoleId(), e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, "修改角色权限失败.", e);
        }
    }

    @PostMapping
    @PreAuthorize("@auth.permission('role:add')")
    @ApiOperation("添加角色")
    public Result<String> add(@RequestBody @Valid RoleAddParam param){
        try {
            roleService.addRole(param);
            return response(ApiEnum.ADD_SUCCESS, "添加角色成功");
        } catch (Exception e) {
            errorLog(log, e, "添加角色 '{}' 信息失败 {}", param.getCode(), e.getMessage());
            return failure(ApiEnum.ADD_ERROR, "添加角色失败", e);
        }
    }

    @PutMapping
    @PreAuthorize("@auth.permission('role:update')")
    @ApiOperation("修改角色")
    public Result<String> update(@RequestBody @Valid RoleUpdateParam param){
        try {
            roleService.updateRole(param);
            return response(ApiEnum.UPDATE_SUCCESS, "更新角色成功");
        } catch (Exception e) {
            errorLog(log, e, "更新角色 '{}' 信息失败 {}", param.getRoleId(), e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, "更新角色失败.", e);
        }
    }

    @PutMapping("{roleId}/{status}")
    @PreAuthorize("@auth.permission('role:update')")
    @ApiOperation("修改角色状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", paramType = "path", required = true),
            @ApiImplicitParam(name = "status", value = "状态（1启用, 0停用）", paramType = "path", required = true)
    })
    public Result<String> updateStatus(@PathVariable("roleId") String roleId,
                                       @PathVariable("status") Integer status){
        String message = status == 1 ? "启用" : "禁用";
        try {
            roleService.updateStatus(roleId, status);
            return success(ApiEnum.UPDATE_SUCCESS, message + "成功");
        } catch (Exception e) {
            errorLog(log, e, "修改角色状态失败. {}", e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, message + "失败", e);
        }
    }


    @DeleteMapping("/{roleId}")
    @PreAuthorize("@auth.permission('role:delete')")
    @ApiOperation("删除角色")
    @ApiImplicitParam(name = "roleId", value = "角色id", paramType = "path", required = true)
    public Result<String> delete(@PathVariable("roleId") String roleId){
        try {
            roleService.deleteRole(roleId);
            return response(ApiEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            errorLog(log, e, "删除角色失败. {}", e.getMessage());
            return failure(ApiEnum.DELETE_ERROR, "删除失败", e);
        }
    }

}
