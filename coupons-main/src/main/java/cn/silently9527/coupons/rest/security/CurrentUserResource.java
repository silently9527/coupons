package cn.silently9527.coupons.rest.security;

import cn.silently9527.coupons.core.security.CurrentUserService;
import cn.silently9527.coupons.core.security.MenuService;
import cn.silently9527.coupons.core.security.model.MenuTree;
import cn.silently9527.coupons.rest.common.BaseResource;
import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import cn.silently9527.coupons.rest.model.param.user.UserUpdatePasswordParam;
import cn.silently9527.coupons.rest.security.model.param.CurrentUserInfoUpdatedParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.util.List;
import java.util.Objects;
import static cn.silently9527.coupons.rest.common.Result.*;
import static cn.silently9527.coupons.rest.common.ResultUtils.errorLog;


/**
 * @author starBlues
 * @version 1.0
 */
@RestController
@RequestMapping(BaseResource.API + "current-user")
@Api(tags = "当前认证的用户接口")
@AllArgsConstructor
@Slf4j
public class CurrentUserResource extends BaseResource {

    private final CurrentUserService userService;
    private final MenuService menuService;

    @GetMapping("/menu-tree")
    @ApiOperation("查询菜单树")
    public Result<List<MenuTree>> getMenuTree(@RequestParam("havePluginMenu") Boolean havePluginMenu) {
        try {
            List<MenuTree> menuTrees = menuService.getMenuTreeByCurrentUser(havePluginMenu);
            return success(ApiEnum.GET_SUCCESS, menuTrees);
        } catch (Exception e) {
            errorLog(log, e, "获取菜单树失败.");
            return failure(ApiEnum.GET_ERROR, e);
        }
    }

    @PutMapping("/password")
    @ApiOperation("修改密码")
    public Result<String> updatePassword(@RequestBody @Valid UserUpdatePasswordParam param){
        try {
            if(!Objects.equals(param.getNewPassword(), param.getConfirmNewPassword())){
                return failure("两次密码输入不一致");
            }
            userService.updateCurrentUserPassword(param.getOldPassword(), param.getNewPassword());
            return success("修改密码成功");
        } catch (Exception e) {
            errorLog(log, e, "修改密码失败. {}", e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, "修改密码失败", e);
        }
    }

    @PostMapping("/avatar")
    @ApiOperation("修改头像")
    public Result<String> updateAvatar(@RequestParam(value = "avatarFile") MultipartFile avatarFile){
        try {
            String fileName = userService.updateAvatar(avatarFile);
            return success(ApiEnum.UPDATE_SUCCESS, fileName,"修改头像成功");
        } catch (Exception e) {
            errorLog(log, e, "修改头像失败. {}", e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, "修改头像失败", e);
        }
    }

    @PutMapping("/basic-info")
    @ApiOperation("修改当前用户信息")
    public Result<String> updateUserInfo(@RequestBody @Valid CurrentUserInfoUpdatedParam param){
        try {
            userService.updateInfo(param);
            return response(ApiEnum.OPERATE_SUCCESS, "更新成功");
        } catch (Exception e) {
            errorLog(log, e, "更新用用户信息失败 {}", e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, "更新失败.", e);
        }
    }



}
