package cn.silently9527.coupons.rest.model.param.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 修改菜单的参数
 * @author starBlues
 * @version 1.0
 */
@Data
@ApiModel("修改角色参数")
public class MenuUpdatedParam {
    /**
     * 菜单id
     */
    @ApiModelProperty(name = "menuId", value = "菜单id", required = true)
    @NotEmpty(message = "菜单id不能为空")
    private String menuId;

    /**
     * 父菜单id
     */
    @ApiModelProperty(name = "parentId", value = "父菜单id", required = true)
//    @NotEmpty(message = "父菜单不能为空")
    private String parentId;

    /**
     * 类型(1: 目录、2: 菜单、3: 权限)
     */
    @ApiModelProperty(name = "type", value = "菜单类型", required = true)
    @NotNull(message = "类型不能为空")
    private Integer type;

    /**
     * 名称
     */
    @ApiModelProperty(name = "title", value = "菜单名称", required = true)
    @NotEmpty(message = "菜单名称不能为空")
    private String title;


    /**
     * 权限标识, 多个可用逗号分隔
     */
    @ApiModelProperty(name = "permissions", value = "权限标识", required = true)
    @NotEmpty(message = "权限标识不能为空")
    private String permissions;

    /**
     * vue组件名称, 只有类型为1、2才有值
     */
    @ApiModelProperty(name = "component", value = "vue组件名称")
    private String component;

    /**
     * 排序，数字越小越靠前
     */
    @ApiModelProperty(name = "orderNum", value = "排序", required = true)
    @NotNull(message = "排序不能为空")
    private Integer orderNum;

    /**
     * 图标
     */
    @ApiModelProperty(name = "icon", value = "图标")
    private String icon;

    /**
     * 路径地址
     */
    @ApiModelProperty(name = "path", value = "路径地址")
    private String path;
}
