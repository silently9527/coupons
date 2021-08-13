package com.huaan9527.mall.webapi.vos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CarouselVo implements Serializable {
    private Long id;
    private String title;
    private String image;
    private String backgroundColor;
    private String url;
    private String urlType;
    private int top;
}
