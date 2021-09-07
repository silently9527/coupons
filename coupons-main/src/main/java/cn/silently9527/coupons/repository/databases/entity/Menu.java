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
 * 菜单模型表
 * </p>
 *
 * @author starblues
 * @since 2020-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @TableId(value = "menu_id", type = IdType.ASSIGN_UUID)
    private String menuId;

    /**
     * 父菜单id,如果是顶级菜单, 则为0
     */
    private String parentId;

    /**
     * 类型(1: 目录、2: 菜单、3: 权限)
     */
    private Integer type;

    /**
     * 名称
     */
    private String title;


    /**
     * 权限标识, 多个可用逗号分隔
     */
    private String permissions;

    /**
     * vue组件名称, 只有类型为1、2才有值
     */
    private String component;

    /**
     * 排序，数字越小越靠前
     */
    private Integer orderNum;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路径地址
     */
    private String path;

    /**
     * 是否启用(1启用, 0禁用)
     */
    private Integer enable;

    /**
     * 是否被删除(1被删除, 0未被删除)
     */
    private Integer deleted;

    /**
     * html target 属性(_blank, _self, _parent, _top)
     */
    private String htmlTarget;

    /**
     * 是否为插件菜单(1是，0不是)
     */
    private Integer pluginMenu;

    /**
     * 插件id
     */
    private String pluginId;

    /**
     * 插件界面app名称
     */
    private String pluginAppName;

    /**
     * 插件界面路径
     */
    private String pluginAppPath;

    /**
     * 插件根路由
     */
    private String pluginRootRouting;

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


}
