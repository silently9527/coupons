package cn.silently9527.coupons.core.security.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.silently9527.coupons.config.prop.SystemProp;
import cn.silently9527.coupons.core.exception.BusinessException;
import cn.silently9527.coupons.core.security.RoleMenuService;
import cn.silently9527.coupons.core.security.UserRoleService;
import cn.silently9527.coupons.repository.databases.entity.Role;
import cn.silently9527.coupons.repository.databases.entity.RoleMenu;
import cn.silently9527.coupons.repository.databases.mapper.RoleMapper;
import cn.silently9527.coupons.core.security.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.silently9527.coupons.repository.databases.mapper.UserMapper;
import cn.silently9527.coupons.rest.model.param.role.RoleAddParam;
import cn.silently9527.coupons.rest.model.param.role.RoleMenuUpdateParam;
import cn.silently9527.coupons.rest.model.param.role.RoleUpdateParam;
import cn.silently9527.coupons.utils.IDUtils;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author starblues
 * @since 2020-12-25
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMenuService roleMenuService;
    private final UserRoleService userRoleService;
    private final UserMapper userMapper;
    private final SystemProp systemProp;


    @Override
    public boolean exist(String roleCode) {
        if(StrUtil.isEmpty(roleCode)){
            return false;
        }
        Wrapper<Role> wrapper = Wrappers.<Role>lambdaQuery()
                .eq(Role::getCode, roleCode);

        return count(wrapper) > 0;
    }

    @Override
    public synchronized void addRole(RoleAddParam param) throws Exception {
        checkRoleCode(param.getCode());
        Role role = new Role();
        BeanUtils.copyProperties(param, role);
        role.setDeleted(0);
        save(role);
    }

    @Override
    public synchronized void updateRole(RoleUpdateParam param) throws Exception {
        Role role = getById(param.getRoleId());
        if(!Objects.equal(role.getCode(), param.getCode())){
            checkRoleCode(param.getCode());
        }
        checkSuperRole(role);
        Role updateBean = new Role();
        BeanUtils.copyProperties(param, updateBean);
        updateById(updateBean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized void deleteRole(String roleId) throws Exception {
        if(StrUtil.isEmpty(roleId)){
            return;
        }
        Role role = getById(roleId);
        if(role == null){
            return;
        }
        checkSuperRole(role);
        // 先删除角色, 再删除关联权限和菜单
        removeById(roleId);
        roleMenuService.removeByRoleId(roleId);
        // 解除该角色对应的用户
        userRoleService.removeByRoleId(roleId);
    }

    @Transactional
    @Override
    public synchronized void updateStatus(String roleId, Integer status) throws Exception {
        if(StrUtil.isEmpty(roleId)){
            return;
        }
        Preconditions.checkNotNull(status, "状态不能为空");
        Preconditions.checkArgument(status == 0 || status == 1,
                "状态标志错误：" + status);
        Role role = getById(roleId);
        if(role == null){
            throw new BusinessException("没有发现角色: " + roleId);
        }
        checkSuperRole(role);
        if(Objects.equal(role.getStatus(), status)){
            return;
        }
        Role updateBean = new Role();
        updateBean.setRoleId(roleId);
        updateBean.setStatus(status);
        updateById(updateBean);
        // 修改角色对应的用户状态
        userMapper.updateStatusByRoleId(roleId, status);
    }

    @Override
    public synchronized void updateRoleMenu(RoleMenuUpdateParam param) throws Exception {
        Role role = getById(param.getRoleId());
        if(role == null){
            throw new BusinessException("没有发现角色: " + param.getRoleId());
        }
        checkSuperRole(role);
        roleMenuService.removeByRoleId(param.getRoleId());
        Set<String> menus = param.getMenus();
        if(menus == null || menus.isEmpty()){
            return;
        }
        List<RoleMenu> roleMenus = menus.stream()
                .map(m -> {
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setId(IDUtils.uuid());
                    roleMenu.setRoleId(param.getRoleId());
                    roleMenu.setMenuId(m);
                    roleMenu.setIsParent(0);
                    return roleMenu;
                })
                .collect(Collectors.toList());


        Set<String> parentMenus = param.getParentMenus();
        if(parentMenus != null){
            for (String parentMenuId : parentMenus) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setId(IDUtils.uuid());
                roleMenu.setRoleId(param.getRoleId());
                roleMenu.setMenuId(parentMenuId);
                roleMenu.setIsParent(1);
                roleMenus.add(roleMenu);
            }
        }

        roleMenuService.saveBatch(roleMenus);
    }

    private void checkSuperRole(Role role) throws Exception{
        String superAdminCode = systemProp.getSuperRoleCode();
        if(Objects.equal(superAdminCode, role.getCode())){
            throw new BusinessException("不能操作超级管理角色");
        }
    }

    private void checkRoleCode(String roleCode) throws Exception{
        if(exist(roleCode)){
            throw new BusinessException("已经存在角色编号：" + roleCode);
        }
    }

}