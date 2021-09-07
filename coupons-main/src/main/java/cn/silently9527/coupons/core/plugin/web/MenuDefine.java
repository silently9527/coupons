package cn.silently9527.coupons.core.plugin.web;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;
import lombok.Getter;

/**
 * 菜单定义bean
 * @author starBlues
 * @version 1.0
 */
@Getter
public class MenuDefine {


    /**
     * 父菜单id, 如果是顶级菜单, 则为0
     */
    private String parentId;


    /**
     * 菜单类型
     */
    private Type type;

    /**
     * 名称
     */
    private String title;

    /**
     * 路径地址
     */
    private String path;

    /**
     * 排序，数字越小越靠前
     */
    private Integer orderNum;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 如果有值, 则当前菜单可跳转。html target 属性: _blank, _self, _parent, _top
     */
    private String htmlTarget;


    public MenuDefine(String parentId, Type type, String title, String path) {
        Preconditions.checkArgument(!StrUtil.isEmpty(parentId), "parentId 不能为空");
        Preconditions.checkNotNull(type, "type 不能为空");
        Preconditions.checkArgument(!StrUtil.isEmpty(title), "title 不能为空");
        Preconditions.checkArgument(!StrUtil.isEmpty(path), "path 不能为空");
        this.parentId = parentId;
        this.type = type;
        this.title = title;
        this.path = path;
    }

    public MenuDefine setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
        return this;
    }

    public MenuDefine setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public MenuDefine setEnable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public MenuDefine setHtmlTarget(String htmlTarget) {
        this.htmlTarget = htmlTarget;
        return this;
    }

    public enum Type{
        /**
         * 目录
         */
        DIRECTORY,

        /**
         * 菜单
         */
        MENU
    }

}
