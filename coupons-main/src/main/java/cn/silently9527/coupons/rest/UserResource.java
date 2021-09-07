package cn.silently9527.coupons.rest;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.silently9527.coupons.core.security.RoleService;
import cn.silently9527.coupons.core.security.UserRoleService;
import cn.silently9527.coupons.core.security.UserService;
import cn.silently9527.coupons.repository.databases.entity.Role;
import cn.silently9527.coupons.repository.databases.entity.UserRole;
import cn.silently9527.coupons.repository.databases.model.UserHasRole;
import cn.silently9527.coupons.rest.common.BaseResource;
import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import cn.silently9527.coupons.rest.model.param.user.UserAddParam;
import cn.silently9527.coupons.rest.model.param.user.UserPageParam;
import cn.silently9527.coupons.rest.model.param.user.UserResetPasswordParam;
import cn.silently9527.coupons.rest.model.param.user.UserUpdateParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import java.util.List;

import static cn.silently9527.coupons.rest.common.Result.*;
import static cn.silently9527.coupons.rest.common.ResultUtils.errorLog;

/**
 * 用户资源接口
 * @author starBlues
 * @version 1.0
 * @since 2020-07-24
 */
@RestController
@RequestMapping(BaseResource.API + "user")
@Api(tags = "用户接口")
@AllArgsConstructor
@Slf4j
public class UserResource extends BaseResource {

    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;

    @GetMapping()
    @PreAuthorize("@auth.permission('user:query')")
    @ApiOperation("分页条件查询用户")
    public Result<IPage<UserHasRole>> pageQuery(@Valid UserPageParam param) {
        return success(ApiEnum.GET_SUCCESS, userService.getPage(param));
    }

    @PostMapping
    @PreAuthorize("@auth.permission('user:add')")
    @ApiOperation("添加用户")
    public Result<String> add(@RequestBody @Valid UserAddParam param){
        try {
            userService.addUser(param);
            return response(ApiEnum.ADD_SUCCESS);
        } catch (Exception e) {
            errorLog(log, e, "新增用户 '{}' 信息失败 {}", param.getUsername(), e.getMessage());
            return failure(ApiEnum.ADD_ERROR, "新增用户失败", e);
        }
    }

    @GetMapping("/enable-roles")
    @PreAuthorize("@auth.permission('user:update')")
    @ApiOperation(value = "查询启动的角色", tags = "修改用户时, 查询所有启用的角色")
    public Result<List<Role>> getRoleList() {
        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery()
                .eq(Role::getDeleted, 0)
                .eq(Role::getStatus, 1)
                .orderByDesc(Role::getGmtCreated);
        return success(ApiEnum.GET_SUCCESS, roleService.list(wrapper));
    }

    @GetMapping("/{userId}/role")
    @PreAuthorize("@auth.permission('user:update')")
    @ApiOperation(value = "查询用户所属角色", tags = "修改用户时, 查询当前用户所属的角色")
    public Result<List<UserRole>> getRoleByUser(@PathVariable("userId") String userId) {
        return success(ApiEnum.GET_SUCCESS, userRoleService.getByUserId(userId));
    }

    @PutMapping
    @PreAuthorize("@auth.permission('user:update')")
    @ApiOperation("修改用户")
    public Result<String> update(@RequestBody @Valid UserUpdateParam param){
        try {
            userService.updateUser(param);
            return response(ApiEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            errorLog(log, e, "更新用户 '{}' 信息失败 {}", param.getUserId(), e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, "更新用户失败", e);
        }
    }

    @PutMapping("{userId}/{status}")
    @PreAuthorize("@auth.permission('user:update')")
    @ApiOperation("修改用户状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "path", required = true),
            @ApiImplicitParam(name = "status", value = "状态（1启用, 0停用）", paramType = "path", required = true)
    })
    public Result<Role> updateStatus(@PathVariable("userId") String userId, @PathVariable("status") Integer status){
        String message = status == 1 ? "启用" : "禁用";
        try {
            userService.updateStatus(userId, status);
            return response(ApiEnum.UPDATE_SUCCESS, message + "成功");
        } catch (Exception e) {
            errorLog(log, e, "修改用户状态失败. {}", e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, message + "失败: ", e);
        }
    }


    @PutMapping("/resetPassword")
    @PreAuthorize("@auth.permission('user:resetPassword')")
    @ApiOperation("重置密码")
    public Result<Role> resetPassword(@RequestBody @Valid UserResetPasswordParam param){
        try {
            userService.resetPassword(param.getUserId(), param.getPassword());
            return response(ApiEnum.UPDATE_SUCCESS, "重置密码成功");
        } catch (Exception e) {
            errorLog(log, e, "重置密码失败. {}", e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, "重置密码失败", e);
        }
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("@auth.permission('user:delete')")
    @ApiOperation("删除用户")
    @ApiImplicitParam(name = "userId", value = "用户id", paramType = "path", required = true)
    public Result<String> deleteUser(@PathVariable("userId") String userId){
        try {
            userService.delete(userId);
            return response(ApiEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            errorLog(log, e, "删除用户 '{}' 失败. {}", userId, e.getMessage());
            return failure(ApiEnum.DELETE_ERROR, e);
        }
    }

}
