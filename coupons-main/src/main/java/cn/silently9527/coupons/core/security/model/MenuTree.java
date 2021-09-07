package cn.silently9527.coupons.core.security.model;

import cn.silently9527.coupons.repository.databases.entity.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 菜单树
 * @author starBlues
 * @version 1.0
 * @since 2020-12-31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuTree extends Menu {

    private List<MenuTree> children;

}
