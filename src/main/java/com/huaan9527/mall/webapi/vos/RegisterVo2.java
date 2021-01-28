package com.huaan9527.mall.webapi.vos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RegisterVo2 implements Serializable {
    @NotNull(message = "手机号不能为空")
    private String mobile;
    private String inviteCode;
    @NotNull(message = "验证码不能为空")
    private String verifyCode;
    @NotNull(message = "签名不能为空")
    private String sign;
}
