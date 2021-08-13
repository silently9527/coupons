package com.huaan9527.mall.webapi.vos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductVo implements Serializable {
    private Long id;
    private String title;
    private double actualPrice;
    private double originalPrice;
    private int monthSales;
    private int dailySales;
    private int shopType;
    private int couponReceiveNum;
    private double couponPrice;
    private String couponStartTime;
    private String couponEndTime;
    private String desc;
    private String shopName;
    private double descScore;
    private double serviceScore;
    private double shipScore;
    private String mainPic;
    private String goodsId;
    private boolean favorite;
    private String imgs;
    private String marketingMainPic;
    private String brandName;

}
