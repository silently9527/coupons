package cn.silently9527.coupons.rest.common.enums;


import cn.silently9527.coupons.rest.common.ResponseEnum;

/**
 * 自定义Api接口格式响应
 * @author starBlues
 * @version 1.0
 */
public enum ApiEnum implements ResponseEnum {


    // 响应成功
    SUCCESS(SUCCESS_CODE, "成功"),
    // 响应失败
    ERROR(0, "失败"),

    // 创建资源成功
    ADD_SUCCESS(SUCCESS_CODE, "新增成功"),
    // 创建资源失败
    ADD_ERROR(0, "新增失败"),
    // 创建资源失败，主键冲突
    ADD_ERROR_DUPLICATE_ID(0, "新增失败,主键已经存在"),
    // 创建资源失败，传入的数据为空
    ADD_ERROR_DATA_IS_EMPTY(0, "新增失败,传入的数据为空"),
    // 创建资源失败，参数不合法
    ADD_ERROR_ILLEGAL_PARAMS(0, "新增失败,参数不合法"),

    // 删除资源成功
    DELETE_SUCCESS(SUCCESS_CODE, "删除成功"),
    // 删除资源失败
    DELETE_ERROR(0, "删除失败"),
    // 删除资源失败
    DELETE_ERROR_DATA_IS_NOT_EXISTED(0, "删除失败,要删除的数据不存在"),
    //从Job任务中删除失败
    DELETE_ERROR_FROM_JOB(0, "从Job任务中删除失败"),

    // 更新资源成功
    UPDATE_SUCCESS(SUCCESS_CODE, "修改成功"),
    // 更新资源失败
    UPDATE_ERROR(0, "修改失败"),
    // 更新资源失败，传入的数据为空
    UPDATE_ERROR_DATA_IS_EMPTY(0, "修改失败,传入的数据为空"),
    // 更新资源失败，参数不合法
    UPDATE_ERROR_ILLEGAL_PARAMS(0, "修改失败,参数不合法"),

    //更新成功，但是添加Job任务失败
    UPDATE_SUCCESS_BUT_JOB_ERROR(0, "修改成功，但是添加Job任务失败"),

    // 获取资源成功
    GET_SUCCESS(SUCCESS_CODE, "获取成功"),
    // 获取资源失败
    GET_ERROR(0, "获取失败"),
    // 获取资源失败
    GET_ERROR_ILLEGAL_QUERY_PARAMS(0, "获取失败,请求参数不合法"),


    // 操作成功
    OPERATE_SUCCESS(SUCCESS_CODE, "操作成功"),
    // 操作失败
    OPERATE_ERROR(0, "操作失败"),

    /**
     * feign 调用失败
     */
    FEIGN_ERROR(0, "feign 调用失败, 进入熔断机制")

    ;

    private Integer code;

    private String message;

    ApiEnum(Integer code, String message) {
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
