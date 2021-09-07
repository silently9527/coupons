package cn.silently9527.coupons.core.security;

import cn.silently9527.coupons.repository.databases.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.silently9527.coupons.rest.model.param.role.RoleAddParam;
import cn.silently9527.coupons.rest.model.param.role.RoleMenuUpdateParam;
import cn.silently9527.coupons.rest.model.param.role.RoleUpdateParam;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author starblues
 * @since 2020-12-31
 */
public interface RoleService extends IService<Role> {

    /**
     * 判断角色编号是否存在
     * @param roleCode 角色编号
     * @return 是否存在
     */
    boolean exist(String roleCode);

    /**
     * 添加角色
     * @param param 添加参数
     * @throws Exception Exception 异常
     */
    void addRole(RoleAddParam param) throws Exception;

    /**
     * 修改角色
     * @param param 修改参数
     * @throws Exception 异常
     */
    void updateRole(RoleUpdateParam param) throws Exception;

    /**
     * 删除角色
     * @param roleId 角色id
     * @throws Exception 删除角色异常
     */
    void deleteRole(String roleId) throws Exception;


    /**
     * 修改角色状态
     * @param roleId 角色id
     * @param status 角色状态
     * @throws Exception 修改异常
     */
    void updateStatus(String roleId, Integer status) throws Exception;

    /**
     * 修改角色权限
     * @param param  修改角色权限bean
     */
    void updateRoleMenu(RoleMenuUpdateParam param) throws Exception;
}
