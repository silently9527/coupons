package cn.silently9527.coupons.core.security;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.silently9527.coupons.repository.databases.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.silently9527.coupons.core.security.model.AuthUserInfo;
import cn.silently9527.coupons.repository.databases.model.UserHasRole;
import cn.silently9527.coupons.rest.model.param.user.UserAddParam;
import cn.silently9527.coupons.rest.model.param.user.UserPageParam;
import cn.silently9527.coupons.rest.model.param.user.UserUpdateParam;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author starblues
 * @since 2020-12-31
 */
public interface UserService extends IService<User>, UserDetailsService {

    /**
     * 分页查询用户列表
     * @param param 参数
     * @return 分页结果
     */
    IPage<UserHasRole> getPage(UserPageParam param);


    /**
     * 通过用户名获取用户
     * @param username 用户名
     * @return 用户
     */
    User getByUsername(String username);


    /**
     * 得到当前认证后的用户信息
     * @return AuthUserInfo
     */
    AuthUserInfo getAuthUserInfo() throws Exception;


    /**
     * 添加用户
     * @param param 添加用户参数
     * @throws Exception 添加异常
     */
    void addUser(UserAddParam param) throws Exception;


    /**
     * 修改用户
     * @param param 更新用户参数
     * @throws Exception 添加异常
     */
    void updateUser(UserUpdateParam param) throws Exception;

    /**
     * 修改用户状态
     * @param userId 用户id
     * @param status 状态
     * @throws Exception 更新状态异常
     */
    void updateStatus(String userId, Integer status) throws Exception;

    /**
     * 重置用户密码
     * @param userId 用户id
     * @param newPassword 重置的新密码
     * @throws Exception 重置密码异常
     */
    void resetPassword(String userId, String newPassword) throws Exception;



    /**
     * 通过用户id删除用户
     * @param userId 用户id
     */
    void delete(String userId) throws Exception;

    /**
     * 得到超级管理员用户
     * @return User
     */
    User getSuperAdmin();

}
