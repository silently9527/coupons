package com.huaan9527.mall.webapi.vos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmSMSCodeVo {
    private String appkey;
    private String phone;
    private String zone;
    private String code;
}
