package com.huaan9527.mall.webapi.vos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SendEmailCodeVo implements Serializable {
    @NotNull
    private String mobile;
    @NotNull
    private String email;

}
