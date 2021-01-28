package com.huaan9527.mall.webapi.domain.enums;

public enum DataType {
    Collocation("搭配"), Product("商品");

    private String label;

    DataType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
