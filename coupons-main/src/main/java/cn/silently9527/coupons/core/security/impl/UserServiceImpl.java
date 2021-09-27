package cn.silently9527.coupons.core.security.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.silently9527.coupons.config.prop.SystemProp;
import cn.silently9527.coupons.config.security.PreAuthorizeService;
import cn.silently9527.coupons.core.exception.BusinessException;
import cn.silently9527.coupons.core.security.UserRoleService;
import cn.silently9527.coupons.repository.databases.entity.Menu;
import cn.silently9527.coupons.repository.databases.entity.Role;
import cn.silently9527.coupons.repository.databases.entity.User;
import cn.silently9527.coupons.repository.databases.entity.UserRole;
import cn.silently9527.coupons.repository.databases.mapper.MenuMapper;
import cn.silently9527.coupons.repository.databases.mapper.RoleMapper;
import cn.silently9527.coupons.repository.databases.mapper.UserMapper;
import cn.silently9527.coupons.core.security.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.silently9527.coupons.core.security.model.AuthUserInfo;
import cn.silently9527.coupons.core.security.model.PermissionGrantedAuthority;
import cn.silently9527.coupons.core.security.model.RoleGrantedAuthority;
import cn.silently9527.coupons.core.security.model.UserDetailsModel;
import cn.silently9527.coupons.repository.databases.model.UserHasRole;
import cn.silently9527.coupons.rest.model.param.user.UserAddParam;
import cn.silently9527.coupons.rest.model.param.user.UserPageParam;
import cn.silently9527.coupons.rest.model.param.user.UserUpdateParam;
import cn.silently9527.coupons.utils.AuthUtils;
import cn.silently9527.coupons.utils.IDUtils;
import cn.silently9527.coupons.utils.TimeUtil;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author starblues
 * @since 2020-12-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    private final RoleMapper roleMapper;
    private final UserRoleService userRoleService;
    private final MenuMapper menuMapper;
    private final SystemProp systemProp;
    private final PasswordEncoder passwordEncoder;

    private User superUser;

    public UserServiceImpl(RoleMapper roleMapper,
                           UserRoleService userRoleService,
                           MenuMapper menuMapper,
                           SystemProp systemProp,
                           PasswordEncoder passwordEncoder) {
        this.roleMapper = roleMapper;
        this.userRoleService = userRoleService;
        this.menuMapper = menuMapper;
        this.systemProp = systemProp;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public IPage<UserHasRole> getPage(UserPageParam param) {
        IPage<?> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        return this.baseMapper.getUserAndRoleInfo(page, param.getName(), param.getUsername(),
                param.getStatus(), param.getLocked());
    }

    @Override
    public synchronized User getByUsername(String username) {
        Wrapper<User> wrapper = Wrappers.<User>query()
                .eq("username", username);
        return getOne(wrapper);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StrUtil.isEmpty(username)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        User sysUser = query().eq("username", username)
                .one();
        if (sysUser != null) {
            return new UserDetailsModel(sysUser, getPermissionModels(sysUser));
        } else {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
    }


    @Override
    public AuthUserInfo getAuthUserInfo() {
        String currentUsername = AuthUtils.getCurrentUsername();
        if (StrUtil.isEmpty(currentUsername)) {
            throw new BusinessException("没有发现当前授权的用户");
        }
        Collection<? extends GrantedAuthority> authorities = AuthUtils.getCurrentGrantedAuthority();
        Set<String> roles = Sets.newHashSet();
        Set<String> permissions = Sets.newHashSet();
        if (!authorities.isEmpty()) {
            for (GrantedAuthority grantedAuthority : authorities) {
                String authority = grantedAuthority.getAuthority();
                if (StrUtil.isEmpty(authority)) {
                    continue;
                }
                if (authority.startsWith(RoleGrantedAuthority.ROLE_GRANTED_AUTHORITY_PREFIX)) {
                    // 角色
                    roles.add(authority.replace(RoleGrantedAuthority.ROLE_GRANTED_AUTHORITY_PREFIX, ""));
                } else {
                    permissions.add(authority);
                }
            }
        }
        User user = getByUsername(currentUsername);
        if (user == null) {
            throw new BusinessException("没有发现用户: " + currentUsername);
        }
        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setUserId(user.getUserId());
        authUserInfo.setName(user.getName());
        authUserInfo.setUsername(currentUsername);
        authUserInfo.setRoles(roles);
        authUserInfo.setPermissions(permissions);
        authUserInfo.setAvatar(user.getAvatar());
        authUserInfo.setLastLoginIp(user.getLastLoginIp());
        authUserInfo.setLastGmtLoginTime(user.getLastGmtLoginTime());
        authUserInfo.setPhone(user.getPhone());
        authUserInfo.setEmail(user.getEmail());
        return authUserInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUser(UserAddParam param) throws Exception {
        Preconditions.checkNotNull(param, "UserAddParam 参数不能为空");
        String username = param.getUsername();
        Preconditions.checkArgument(!StrUtil.isEmpty(username), "用户名不能为空");
        Set<String> roleIds = param.getRoleIds();
        Preconditions.checkNotNull(roleIds, "角色id集合不能为空");
        Preconditions.checkArgument(!roleIds.isEmpty(), "角色id集合不能为空");

        synchronized (this) {
            User databaseUser = getByUsername(username);
            if (databaseUser != null) {
                throw new BusinessException("已经存在用户名: " + username);
            }

            User user = new User();
            BeanUtils.copyProperties(param, user);
            // 设置密码
            String password = user.getPassword();
            if (!StrUtil.isEmpty(password)) {
                user.setPassword(passwordEncoder.encode(password));
            }
            user.setUserId(IDUtils.uuid());
            user.setDeleted(0);
            user.setLocked(0);
            List<UserRole> userRoles = getUserRole(user.getUserId(), roleIds);

            // 保存用户
            save(user);
            // 分配角色
            userRoleService.saveBatch(userRoles);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(UserUpdateParam param) throws Exception {
        Preconditions.checkNotNull(param, "UserAddParam 参数不能为空");
        Preconditions.checkArgument(!StrUtil.isEmpty(param.getUserId()), "用户id不能为空");
        Set<String> roleIds = param.getRoleIds();
        Preconditions.checkNotNull(roleIds, "角色id集合不能为空");
        checkSuperUser(param.getUserId());
        synchronized (this) {
            User databaseUser = getById(param.getUserId());
            if (databaseUser == null) {
                throw new BusinessException("没有发现用户: " + param.getUserId());
            }
            if (!Objects.equals(databaseUser.getUsername(), param.getUsername())) {
                if (getByUsername(param.getUsername()) != null) {
                    throw new BusinessException("已经存在用户名: " + param.getUsername());
                }
            }
            User user = new User();
            BeanUtils.copyProperties(param, user);
            user.setGmtModified(TimeUtil.getNowTimeToSeconds());
            user.setGmtModified(getCurrentUsername());
            updateById(user);
            // 处理角色
            userRoleService.removeByUserId(param.getUserId());
            userRoleService.add(param.getUserId(), param.getRoleIds());
        }
    }


    @Override
    public synchronized void updateStatus(String userId, Integer status) throws Exception {
        Preconditions.checkArgument(!StrUtil.isEmpty(userId), "用户id不能为空");
        if (status == null) {
            return;
        }
        checkSuperUser(userId);
        User databaseUser = getById(userId);
        if (databaseUser == null) {
            throw new BusinessException("没有发现用户: " + userId);
        }
        if (Objects.equals(databaseUser.getStatus(), status)) {
            return;
        }
        User updateBean = new User();
        updateBean.setUserId(userId);
        updateBean.setStatus(status);
        updateBean.setGmtModified(TimeUtil.getNowTimeToSeconds());
        updateBean.setGmtModified(getCurrentUsername());
        updateById(updateBean);
    }

    @Override
    public synchronized void resetPassword(String userId, String newPassword) throws Exception {
        Preconditions.checkArgument(!StrUtil.isEmpty(userId), "用户id不能为空");
        Preconditions.checkArgument(!StrUtil.isEmpty(newPassword), "新密码不能为空");
        if (newPassword.length() < 6) {
            throw new BusinessException("密码不能小于6个字符");
        }
        checkSuperUser(userId);
        User databaseUser = getById(userId);
        if (databaseUser == null) {
            throw new BusinessException("没有发现用户: " + userId);
        }
        User updateBean = new User();
        updateBean.setUserId(userId);
        updateBean.setPassword(passwordEncoder.encode(newPassword));
        updateBean.setGmtModified(TimeUtil.getNowTimeToSeconds());
        updateBean.setGmtModified(getCurrentUsername());
        updateById(updateBean);
    }


    @Override
    public void delete(String userId) throws Exception {
        if (StrUtil.isEmpty(userId)) {
            return;
        }
        checkSuperUser(userId);
        synchronized (this) {
            removeById(userId);
        }
    }

    @Override
    public synchronized User getSuperAdmin() {
        if (superUser != null) {
            return superUser;
        }
        superUser = getById(systemProp.getSuperAdminId());
        return superUser;
    }

    /**
     * 获取当前用户所拥有的权限
     *
     * @param user 系统用户
     * @return 权限集合
     */
    private Set<? extends GrantedAuthority> getPermissionModels(User user) {
        List<Role> roles = roleMapper.getRoleByUserId(user.getUserId());

        if (roles == null || roles.isEmpty()) {
            return null;
        }

        Set<GrantedAuthority> grantedAuthorities = Sets.newHashSet();

        boolean haveSuperRole = false;
        Set<String> roleIds = Sets.newHashSetWithExpectedSize(roles.size());
        for (Role role : roles) {
            if (role == null || StrUtil.isEmpty(role.getCode())) {
                continue;
            }
            String code = role.getCode();
            if (Objects.equals(systemProp.getSuperRoleCode(), code)) {
                haveSuperRole = true;
            }
            grantedAuthorities.add(new RoleGrantedAuthority(role));
            roleIds.add(role.getRoleId());
        }

        if (haveSuperRole) {
            // 如果是超级角色, 则返回 *:* 权限
            grantedAuthorities.add(new PermissionGrantedAuthority(PreAuthorizeService.SUPPER_PERMISSION));
            return grantedAuthorities;
        }

        if (roleIds.isEmpty()) {
            return grantedAuthorities;
        }
        List<Menu> menus = menuMapper.getMenuByRoleIds(roleIds, null);
        // 拥有的权限
        if (menus == null || menus.isEmpty()) {
            return grantedAuthorities;
        }

        for (Menu menu : menus) {
            if (menu == null) {
                continue;
            }
            String permissionsString = menu.getPermissions();
            if (StrUtil.isEmpty(permissionsString)) {
                continue;
            }
            if (permissionsString.contains(",")) {
                List<String> splitPermissions = Splitter.on(",")
                        .trimResults()
                        .omitEmptyStrings()
                        .splitToList(permissionsString);
                for (String splitPermission : splitPermissions) {
                    grantedAuthorities.add(new PermissionGrantedAuthority(splitPermission));
                }
            } else {
                grantedAuthorities.add(new PermissionGrantedAuthority(permissionsString));
            }
        }
        return grantedAuthorities;
    }

    private List<UserRole> getUserRole(String userId, Set<String> roleIds) throws Exception {
        List<Role> roles = roleMapper.selectBatchIds(roleIds);
        if (roles == null || roles.isEmpty()) {
            throw new BusinessException("没有发现角色");
        }
        List<UserRole> userRoles = Lists.newArrayListWithCapacity(roles.size());
        for (Role role : roles) {
            if (role == null) {
                continue;
            }
            if (role.getStatus() == 0) {
                throw new BusinessException("角色'" + role.getName() + "'已经被禁用, 无法分配");
            }
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(role.getRoleId());
            userRoles.add(userRole);
        }
        if (userRoles.isEmpty()) {
            throw new BusinessException("没有发现合适的角色");
        }
        return userRoles;
    }


    public static String getCurrentUsername() {
        String currentUsername = AuthUtils.getCurrentUsername();
        if (StrUtil.isEmpty(currentUsername)) {
            return "NOT_FOUND";
        }
        return currentUsername;
    }

    private void checkSuperUser(String userId) throws Exception {
        String superAdminId = systemProp.getSuperAdminId();
        if (Objects.equals(superAdminId, userId)) {
            throw new BusinessException("不能操作超级管理员");
        }
    }
}
