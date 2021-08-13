package com.huaan9527.mall.webapi.domain;


import com.huaan9527.mall.webapi.domain.enums.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "collocation_product")
@NoArgsConstructor
public class CollocationProduct extends AbstractDomain<Long> {
    private Long collocationId;

    private String tbGoodsId; //淘宝商品id
    private String productStatus = ProductStatus.Up.name();



}
