package com.huaan9527.mall.webapi.domain;


import com.huaan9527.mall.webapi.domain.enums.DataType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "collection")
@NoArgsConstructor
public class Collection extends AbstractDomain<Long> {
    private Long userId;
    private Long dataId; //淘宝商品id  或者  穿搭id
    private String dataType = DataType.Product.name();

}
