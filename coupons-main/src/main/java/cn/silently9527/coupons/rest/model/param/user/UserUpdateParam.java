package cn.silently9527.coupons.rest.model.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 修改角色参数
 * @author starBlues
 * @version 1.0
 */
@Data
@ApiModel("修改用户参数")
public class UserUpdateParam {


    /**
     * 所属角色id集合
     */
    @ApiModelProperty(name = "roleIds", value = "角色id集合", required = true)
    @NotNull(message = "角色id集合不能为空")
    private Set<String> roleIds;

    /**
     * 用户名称
     */
    @ApiModelProperty(name = "userId", value = "用户id", required = true)
    @NotEmpty(message = "用户id不能为空")
    private String userId;

    /**
     * 用户名称
     */
    @ApiModelProperty(name = "name", value = "用户名称", required = true)
    @NotEmpty(message = "用户名称不能为空")
    private String name;

    /**
     * 用户名称
     */
    @ApiModelProperty(name = "username", value = "用户名", required = true)
    @NotEmpty(message = "用户名不能为空")
    private String username;


    /**
     * 帐号状态（1启用, 0停用）
     */
    @ApiModelProperty(name = "status", value = "帐号状态（1启用, 0停用）", required = true)
    @NotNull(message = "帐号状态不能为空")
    private Integer status;

    /**
     * 手机号
     */
    @ApiModelProperty(name = "photo", value = "手机号")
    private String photo;

    /**
     * 邮箱
     */
    @ApiModelProperty(name = "email", value = "邮箱")
    private String email;


}
