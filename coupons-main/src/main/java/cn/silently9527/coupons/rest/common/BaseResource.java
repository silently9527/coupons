package cn.silently9527.coupons.rest.common;

import org.springframework.http.ResponseEntity;

/**
 * restful 接口的公共类
 *
 * @author starBlues
 * @version 1.0
 */
public abstract class BaseResource {

    public static final String API = "/api/";


    /**
     * 返回接口封装 带有浏览器状态码的
     * @param responseEnum HttpStatusEnum 类中的枚举 注意状态码直接时枚举中的code, 定义的状态和浏览器规范的状态码必须一致
     * @param bodyEnum body 体中的枚举。
     * @param bodyMessage body 信息
     * @param bodyData body 返回的数据
     * @param <T> 返回数据的类型
     * @return ResponseEntity
     */
    protected <T> ResponseEntity<Result<T>> responseStatusAndBody(ResponseEnum responseEnum,
                                                                  ResponseEnum bodyEnum,
                                                                  String bodyMessage, T bodyData){
        Result<T> body = Result.<T>toBuilder()
                .data(bodyData)
                .responseEnum(bodyEnum)
                .message(bodyMessage)
                .builder();
        return ResponseEntity.status(responseEnum.getCode())
                .body(body);
    }

    /**
     * 返回接口封装 带有浏览器状态码的
     * @param responseEnum HttpStatusEnum 类中的枚举 注意状态码直接时枚举中的code, 定义的状态和浏览器规范的状态码必须一致
     * @param bodyEnum body 体中的枚举。
     * @param bodyMsg body体信息
     * @param <T> 返回数据的类型
     * @return ResponseEntity
     */
    protected <T> ResponseEntity<Result<T>> responseStatusAndBody(ResponseEnum responseEnum,
                                                                  ResponseEnum bodyEnum,
                                                                  String bodyMsg){
        return this.responseStatusAndBody(responseEnum, bodyEnum, bodyMsg, null);
    }

    /**
     * 返回接口封装 带有浏览器状态码的
     * @param responseEnum HttpStatusEnum 类中的枚举 注意状态码直接时枚举中的code, 定义的状态和浏览器规范的状态码必须一致
     * @param bodyEnum body 体中的枚举。
     * @param <T> 返回数据的类型
     * @return ResponseEntity
     */
    protected <T> ResponseEntity<Result<T>> responseStatusAndBody(ResponseEnum responseEnum, ResponseEnum bodyEnum){
        return this.responseStatusAndBody(responseEnum, bodyEnum, bodyEnum.getMessage(), null);
    }



}
