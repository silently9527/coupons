package com.huaan9527.mall.webapi.domain;

import com.huaan9527.mall.webapi.domain.enums.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User extends AbstractDomain<Long> {
    private String account;
    private String password;

    private String userType;
    private String nick;
    private String mobile;
    private String email;
    private String status = UserStatus.Normal.name();

    private String avatarUrl;
    private String city;
    private String country;
    private String province;
    private int sex;

    /**
     * OAuth2 注册
     *
     * @param connection
     */
    public void signUpFromConnection(Connection<?> connection) {
        UserProfile userProfile = connection.fetchUserProfile();
        this.account = userProfile.getUsername();
        this.email = userProfile.getEmail();
        this.nick = userProfile.getName();
    }
}


