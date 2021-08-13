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
@Table(name = "menu")
@NoArgsConstructor
public class Menu extends AbstractDomain<Long> {
    private String label;
    private String icon;
    private String url;
    private int sort;
    private int status; //0 禁用 1启用
    private String title;
    private int top;
    private String urlType = Urlype.H5.name();
}
