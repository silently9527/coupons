package com.huaan9527.mall.webapi.vos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RegisterVo implements Serializable {
    @NotNull(message = "手机号不能为空")
    private String mobile;
    @NotNull(message = "邮箱不能为空")
    private String email;
    @NotNull
    @Length(min = 6, max = 18, message = "6-18位不含特殊字符的数字、字母组合")
    private String password;
}
