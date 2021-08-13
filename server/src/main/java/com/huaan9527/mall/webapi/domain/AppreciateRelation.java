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
@Table(name = "appreciate_relation")
@NoArgsConstructor
public class AppreciateRelation extends AbstractDomain<Long> {
    public final static Integer ADD_APPRECIATE = 1;
    public final static Integer CANCEL_APPRECIATE = -1;

    private Long userId;
    private Long dataId;
    private String dataType = DataType.Collocation.name();

}
