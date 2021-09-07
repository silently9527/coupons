package cn.silently9527.coupons.rest.common.enums;


import cn.silently9527.coupons.rest.common.ResponseEnum;

/**
 * OAuth2 认证枚举
 * @author starBlues
 * @version 1.0
 * @since 2020-08-07
 */
public enum OAuth2Enum implements ResponseEnum {



    /**
     * 获取token成功
     */
    SUCCESS(SUCCESS_CODE, "获取Token成功"),

    /**
     * 失败
     */
    FAILURE(2, "获取Token失败"),

    /**
     * 无效Token
     */
    INVALID_TOKEN(3, "无效的Token"),

    /**
     * token 过期
     */
    TOKEN_EXPIRED(4, "Token过期"),

    /**
     * 认证失败
     */
    AUTH_FAILED(5, "认证失败");


    private Integer code;
    private String message;


    OAuth2Enum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
