package com.huaan9527.mall.webapi.vos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollocationProductVo implements Serializable {
    private Long productId;
    private String goodsId; //淘宝商品id
    private String mainPic;
    private String marketingMainPic;
    private String title;
    private String actualPrice;
    private String couponPrice;


}
