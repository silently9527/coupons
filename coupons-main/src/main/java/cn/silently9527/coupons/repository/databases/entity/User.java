package cn.silently9527.coupons.repository.databases.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author starblues
 * @since 2020-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_UUID)
    private String userId;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码(加密后)
     */
    private String password;

    /**
     * 帐号状态（1启用, 0停用）
     */
    private Integer status;

    /**
     * 删除标记（1删除, 0 正常）暂未使用
     */
    private Integer deleted;

    /**
     * 是否被锁(小于等于5表示未被锁, 大于5表示被锁)
     */
    private Integer locked;

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
    @TableField(fill = FieldFill.INSERT)
    private String modifiedUser;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String gmtModified;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 上一次登录的ip地址
     */
    private String lastLoginIp;

    /**
     * 上一次登录的时间
     */
    private String lastGmtLoginTime;


}
