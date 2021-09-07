package cn.silently9527.coupons.rest.common.enums;


import cn.silently9527.coupons.rest.common.ResponseEnum;

/**
 * 登录返回界面信息
 *
 * @author starBlues
 * @version 1.0
 */
public enum LoginEnum implements ResponseEnum {

    /**
     * 参数错误
     */
    PARAMETER_ERROR(0, "参数错误"),

    /**
     * 参数错误
     */
    PARAMETER_ERROR_USERNAME(0, "用户名不能为空"),

    /**
     * 参数错误
     */
    PARAMETER_ERROR_PASSWORD(0, "密码不能为空"),

    /**
     * 参数错误
     */
    PARAMETER_ERROR_CHECK_CODE(0, "验证码不能为空"),


    /**
     * 认证成功
     */
    SUCCESS(SUCCESS_CODE, "认证成功"),


    /**
     * 用户名或者密码错误
     */
    UNKNOWN_ACCOUNT(2, "用户名或者密码错误"),

    /**
     * 用户名或者密码错误
     */
    INCORRECT_CREDENTIALS(2, "用户名或者密码错误"),


    /**
     * 用户被锁定
     */
    LOCKED_ACCOUNT(3, "用户已被锁定, 请联系管理员"),

    /**
     * 该用户被禁用
     */
    DISABLED_ACCOUNT(4, "该用户被禁用"),

    /**
     * 登录过期
     */
    OVERDUE_ACCOUNT(5, "登录过期"),

    /**
     * 认证错误
     */
    AUTHENTICATION_ERROR(6, "认证失败"),


    /**
     * 非法访问
     */
    ILLEGAL_ACCESS(7, "非法访问"),


    /**
     * 其他错误
     */
    UNKNOWN_ERROR(-1, "未知错误");




    private Integer code;
    private String message;


    LoginEnum(Integer code, String message) {
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
