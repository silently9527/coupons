package com.huaan9527.mall.webapi.vos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UserVo implements Serializable {
    private Long userId;
    private String nick;
    private String mobile;
    private String email;
    private String status;
    private String userType;

    private String avatarUrl;
    private String city;
    private String country;
    private String province;
    private int sex;


}
