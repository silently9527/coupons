package cn.silently9527.coupons.repository.databases.mapper;

import cn.silently9527.coupons.repository.databases.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author starblues
 * @since 2020-12-31
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 通过用户id获取角色信息
     * @param userId 用户id
     * @return 角色列表
     */
    List<Role> getRoleByUserId(String userId);


}
