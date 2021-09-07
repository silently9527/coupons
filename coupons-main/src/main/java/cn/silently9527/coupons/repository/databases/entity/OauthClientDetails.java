package cn.silently9527.coupons.repository.databases.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 授权客户端表
 * </p>
 *
 * @author starblues
 * @since 2020-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OauthClientDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户端ID
     */
    @TableId(value = "client_id", type = IdType.INPUT)
    private String clientId;

    /**
     * 名称
     */
    private String name;

    /**
     * 资源ID集合,多个资源时用逗号(,)分隔
     */
    private String resourceIds;

    /**
     * 客户端密匙
     */
    private String clientSecret;

    /**
     * 客户端申请的权限范围
     */
    private String scope;

    /**
     * 客户端支持的grant_type
     */
    private String authorizedGrantTypes;

    /**
     * 重定向URI
     */
    private String webServerRedirectUri;

    /**
     * 客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔
     */
    private String authorities;

    /**
     * 访问令牌有效时间值(单位:秒)
     */
    private Integer accessTokenValidity;

    /**
     * 更新令牌有效时间值(单位:秒)
     */
    private Integer refreshTokenValidity;

    /**
     * 预留字段
     */
    private String additionalInformation;

    /**
     * 用户是否自动Approval操作
     */
    private String autoapprove;

    /**
     * 创建用户
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String gmtCreated;

    /**
     * 修改用户
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String modifiedUser;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String gmtModified;

    /**
     * 描述
     */
    private String description;


}
