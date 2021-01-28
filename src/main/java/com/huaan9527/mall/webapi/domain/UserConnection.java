package com.huaan9527.mall.webapi.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "UserConnection")
@NoArgsConstructor
public class UserConnection extends AbstractDomain<Long> {
    @Column(name = "userId")
    private String userId;
    @Column(name = "providerId")
    private String providerId;
    @Column(name = "providerUserId")
    private String providerUserId;
    private Integer rank;
    @Column(name = "displayName")
    private String displayName;
    @Column(name = "profileUrl")
    private String profileUrl;
    @Column(name = "imageUrl")
    private String imageUrl;
    @Column(name = "accessToken")
    private String accessToken;
    private String secret;
    @Column(name = "refreshToken")
    private String refreshToken;
    @Column(name = "expireTime")
    private Long expireTime;

}
