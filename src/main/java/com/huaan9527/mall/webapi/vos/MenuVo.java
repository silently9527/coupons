package com.huaan9527.mall.webapi.vos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MenuVo implements Serializable {
    private Long id;
    private String label;
    private String icon;
    private String url;
    private int status; //0 禁用 1启用
    private String title;
    private int top;
    private String urlType;
}
