package cn.silently9527.coupons.utils;

import cn.hutool.core.util.StrUtil;
import cn.silently9527.coupons.core.security.model.RoleGrantedAuthority;
import com.google.common.collect.Sets;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * 授权工具类
 * @author starBlues
 * @version 1.0
 */
public class AuthUtils {

    private AuthUtils(){}


    public static String getCurrentUsername(){
        Authentication authentication = getCurrentAuth();
        if(authentication == null){
            return null;
        }
        Object principal = authentication.getPrincipal();
        if(principal != null){
            return String.valueOf(principal);
        } else {
            return null;
        }
    }


    public static Collection<? extends GrantedAuthority> getCurrentGrantedAuthority(){
        Authentication authentication = getCurrentAuth();
        if(authentication == null){
            return Collections.emptyList();
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if(authorities != null){
            return authorities;
        } else {
            return Collections.emptyList();
        }
    }

    public static Set<String> getAuthoritySet() {
        Collection<? extends GrantedAuthority> userAuthorities = getCurrentGrantedAuthority();
        return AuthorityUtils.authorityListToSet(userAuthorities);
    }

    public static Authentication getCurrentAuth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Set<String> getCurrentRoleCodes(){
        Collection<? extends GrantedAuthority> authorities = getCurrentGrantedAuthority();
        if(authorities.isEmpty()){
            return Collections.emptySet();
        }
        Set<String> roleCodes = Sets.newHashSetWithExpectedSize(authorities.size());
        for (GrantedAuthority grantedAuthority : authorities) {
            String authority = grantedAuthority.getAuthority();
            if(StrUtil.isEmpty(authority)){
                continue;
            }
            if(authority.startsWith(RoleGrantedAuthority.ROLE_GRANTED_AUTHORITY_PREFIX)){
                // 角色
                roleCodes.add(authority.replace(RoleGrantedAuthority.ROLE_GRANTED_AUTHORITY_PREFIX, ""));
            }
        }
        return roleCodes;
    }

}
