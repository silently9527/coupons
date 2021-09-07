package cn.silently9527.coupons.core.security.model;

import cn.silently9527.coupons.repository.databases.entity.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * 角色授权模型
 * @author starBlues
 * @version 1.0
 * @since 2020-08-07
 */
@Data
public class RoleGrantedAuthority implements GrantedAuthority {

    public static final String ROLE_GRANTED_AUTHORITY_PREFIX = "$$$&&&!!!ROLE_";

    private final String authority;

    public RoleGrantedAuthority(Role role) {
        this.authority = ROLE_GRANTED_AUTHORITY_PREFIX + role.getCode();
    }

    @Override
    public String getAuthority() {
        return authority;
    }


}
