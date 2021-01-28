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
@Table(name = "tag_relation")
@NoArgsConstructor
public class TagRelation extends AbstractDomain<Long> {
    private Long tagId;
    private Long dataId;
    private String dataType = DataType.Collocation.name();

}
