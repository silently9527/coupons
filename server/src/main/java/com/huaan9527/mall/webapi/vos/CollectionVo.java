package com.huaan9527.mall.webapi.vos;

import com.huaan9527.mall.webapi.domain.Collection;
import com.huaan9527.mall.webapi.domain.enums.DataType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CollectionVo implements Serializable {
    private Long collectionId;
    private Long dataId; //淘宝商品id  或者  穿搭id
    private String dataType = DataType.Product.name();

    private Object data;


    public CollectionVo(Collection collection) {
        BeanUtils.copyProperties(collection, this);
        this.collectionId = collection.getId();
    }

}
