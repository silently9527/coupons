package cn.silently9527.coupons.core.security.model;

import lombok.Data;

import java.util.Set;

/**
 * @author starBlues
 * @version 1.0
 * @since 2020-07-06
 */
@Data
public class AuthUserInfo {


    private String name;
    private String username;
    private String avatar;
    private String email;
    private String phone;
    private String lastLoginIp;
    private String lastGmtLoginTime;
    private Set<String> roles;
    private Set<String> permissions;

}