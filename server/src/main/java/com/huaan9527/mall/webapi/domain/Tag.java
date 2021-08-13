package com.huaan9527.mall.webapi.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "tag")
@NoArgsConstructor
public class Tag extends AbstractDomain<Long> {
    private String name;
}
