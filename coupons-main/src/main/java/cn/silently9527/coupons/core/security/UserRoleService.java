package cn.silently9527.coupons.core.security;

import cn.silently9527.coupons.repository.databases.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统用户角色关联表 服务类
 * </p>
 *
 * @author starblues
 * @since 2020-12-31
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 新增映射
     * @param userId 用户id
     * @param roleIds 角色id集合
     */
    void add(String userId, Set<String> roleIds);

    /**
     * 删除角色与用户的关联
     * @param roleId 角色id
     */
    void removeByRoleId(String roleId);


    /**
     * 删除角色与用户的关联
     * @param userId 用户id
     */
    void removeByUserId(String userId);

    /**
     * 删除角色与用户的关联
     * @param userId 用户id
     * @param roleIds 角色id集合
     */
    void removeByRoleId(String userId, Set<String> roleIds);

    /**
     * 通过用户名获取角色关联
     * @param userId 用户id
     * @return List<UserRole>
     */
    List<UserRole> getByUserId(String userId);
}
