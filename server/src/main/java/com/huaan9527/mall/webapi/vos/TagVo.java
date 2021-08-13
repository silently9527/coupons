package com.huaan9527.mall.webapi.vos;

import lombok.Data;

import java.io.Serializable;

@Data
public class TagVo implements Serializable {
    private Long tagId;
    private String name;
}
