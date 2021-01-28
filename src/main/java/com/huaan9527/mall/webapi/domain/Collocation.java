package com.huaan9527.mall.webapi.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "collocation")
@NoArgsConstructor
public class Collocation extends AbstractDomain<Long> {
    public static final String NOTHING_SOURCE_TYPE = "Nothing";
    public static final String YI_XIN_SOURCE_TYPE = "YiXin";

    private int sex; //1 男  0 女
    private String description;
    private String mainImage; //封面图片
    private String images; //多个用分号分隔

    private Long viewCount; //浏览次数
    private Long appreciateCount;  //点赞数

    private String sourceType; //Nothing
    private String sourceId; //matchId


}
