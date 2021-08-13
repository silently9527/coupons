package com.huaan9527.mall.webapi.domain;


import com.huaan9527.mall.webapi.domain.enums.Urlype;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "carousel")
@NoArgsConstructor
public class Carousel extends AbstractDomain<Long> {
    private String title;
    private String image;
    private String backgroundColor;
    private String url;
    private int status; //0 禁用 1启用
    private int top;
    private int sort;
    private String urlType = Urlype.H5.name();
}
