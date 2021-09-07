package cn.silently9527.coupons.core.security.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.silently9527.coupons.repository.databases.entity.UserRole;
import cn.silently9527.coupons.repository.databases.mapper.UserRoleMapper;
import cn.silently9527.coupons.core.security.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户角色关联表 服务实现类
 * </p>
 *
 * @author starblues
 * @since 2020-12-25
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public void add(String userId, Set<String> roleIds) {
        if(roleIds == null || roleIds.isEmpty()){
            return;
        }
        List<UserRole> userRoles = roleIds.stream()
                .filter(roleId -> !StrUtil.isEmpty(roleId))
                .map(roleId -> {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(roleId);
                    return userRole;
                })
                .collect(Collectors.toList());
        if(userRoles.isEmpty()){
            return;
        }
        saveBatch(userRoles);
    }

    @Override
    public void removeByRoleId(String roleId) {
        if(StrUtil.isEmpty(roleId)) {
            return;
        }
        Wrapper<UserRole> wrapper = Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getRoleId, roleId);
        remove(wrapper);
    }

    @Override
    public void removeByUserId(String userId) {
        if(StrUtil.isEmpty(userId)) {
            return;
        }
        Wrapper<UserRole> wrapper = Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, userId);
        remove(wrapper);
    }

    @Override
    public void removeByRoleId(String userId, Set<String> roleIds) {
        if(StrUtil.isEmpty(userId)) {
            return;
        }
        if(roleIds == null || roleIds.isEmpty()){
            return;
        }
        Wrapper<UserRole> wrapper = Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, userId)
                .in(UserRole::getRoleId, roleIds);
        remove(wrapper);
    }

    @Override
    public List<UserRole> getByUserId(String userId) {
        if(StrUtil.isEmpty(userId)) {
            return Collections.emptyList();
        }
        Wrapper<UserRole> wrapper = Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, userId);
        return list(wrapper);
    }
}
