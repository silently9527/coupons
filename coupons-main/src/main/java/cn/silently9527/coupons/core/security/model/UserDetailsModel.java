package cn.silently9527.coupons.core.security.model;

import cn.silently9527.coupons.repository.databases.entity.User;
import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * 用户详细模型
 * @author starBlues
 * @version 1.0
 * @since 2020-08-07
 */
public class UserDetailsModel implements UserDetails {


    private final User user;
    private final Set<? extends GrantedAuthority> grantedAuthorities;


    public UserDetailsModel(User user) {
        this(user, null);
    }

    public UserDetailsModel(User user, Set<? extends GrantedAuthority> grantedAuthorities) {
        this.user = user;

        if(grantedAuthorities == null){
            this.grantedAuthorities = Sets.newHashSet();
        } else {
            this.grantedAuthorities = grantedAuthorities;
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getLocked() == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        Integer status = user.getStatus();
        return status != null && status == 1;
    }

    public User getUser() {
        return user;
    }
}
