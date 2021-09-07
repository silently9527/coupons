package cn.silently9527.coupons.rest.model.param.oauthclient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 添加-修改参数
 * @author starBlues
 * @version 1.0
 */
@Data
@ApiModel("添加-修改参数")
public class OauthClientUpdatedParam {

    /**
     * 客户端ID
     */
    @ApiModelProperty(name = "clientId", value = "客户端id", required = true)
    @NotEmpty(message = "客户端id不能为空")
    private String clientId;

    /**
     * 名称
     */
    @ApiModelProperty(name = "name", value = "名称", required = true)
    @NotEmpty(message = "名称不能为空")
    private String name;

    /**
     * 客户端秘钥
     */
    @ApiModelProperty(name = "clientSecret", value = "秘钥")
    private String clientSecret;

    /**
     * 客户端申请的权限范围
     */
    @ApiModelProperty(name = "scope", value = "客户端申请的权限范围", required = true)
    @NotEmpty(message = "权限范围不能为空")
    private String scope;

    /**
     * 客户端支持的grant_type
     */
    @ApiModelProperty(name = "authorizedGrantTypes", value = "授权模式", required = true)
    @NotEmpty(message = "授权模式不能为空")
    private Set<String> authorizedGrantTypes;


    /**
     * 资源ID集合,多个资源时用逗号(,)分隔
     */
    @ApiModelProperty(name = "resourceIds", value = "资源ID集合,多个资源时用逗号(,)分隔")
    private String resourceIds;


    /**
     * 客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔
     */
    @ApiModelProperty(name = "authorities", value = "权限值, 多个用逗号(,)分隔")
    private String authorities;

    /**
     * 访问令牌有效时间值(单位:秒)
     */
    @ApiModelProperty(name = "accessTokenValidity", value = "访问令牌有效时间值(单位:秒)", required = true)
    @NotNull(message = "令牌有效时间不能为空")
    @Min(value = 60,message = "令牌有效时间不能小于60秒")
    private Integer accessTokenValidity;

    /**
     * 更新令牌有效时间值(单位:秒)
     */
    @ApiModelProperty(name = "refreshTokenValidity", value = "刷新令牌有效时间值(单位:秒)", required = true)
    @NotNull(message = "刷新令牌有效时间不能为空")
    @Min(value = 60,message = "刷新令牌有效时间不能小于60秒")
    private Integer refreshTokenValidity;

    /**
     * 描述
     */
    @ApiModelProperty(name = "description", value = "描述")
    private String description;
}
