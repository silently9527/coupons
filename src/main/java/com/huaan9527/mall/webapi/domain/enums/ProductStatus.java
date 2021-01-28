package com.huaan9527.mall.webapi.domain.enums;

public enum ProductStatus {
    Up("上架"), Down("下架");
    private String label;

    ProductStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
