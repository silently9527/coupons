package cn.silently9527.coupons.rest.common;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import static cn.silently9527.coupons.rest.common.ResultUtils.getFailureMessage;


/**
 * rest接口返回的数据模型
 *
 * @author starBlues
 * @version 1.0
 */
@ApiModel(value="输出模型", description="输出模型包装")
public class Result<T> implements Serializable{

    /**
     * 返回码
     */
    @ApiModelProperty(value = "返回code码, 约定 1 成功, 0 失败", required = true)
    private Integer code;

    /**
     * 提示信息
     */
    @ApiModelProperty(value = "提示信息", required = true)
    private String message;

    /**
     * 返回的数据
     */
    @ApiModelProperty(value = "输出数据体" , required = true)
    private T data;

    public Result() {
    }

    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    /**
     * 根据ResponseEnum返回数据体
     * @param responseEnum 相应数据的枚举
     * @param <T> 返回数据的类型
     * @return Result
     */
    public static <T> Result<T> response(ResponseEnum responseEnum){
        return Result.<T>toBuilder()
                .responseEnum(responseEnum)
                .builder();
    }

    /**
     * 根据ResponseEnum返回数据体
     * @param responseEnum Result 类中的枚举
     * @param message 提示
     * @param <T> 返回数据的类型
     * @return Result
     */
    public static <T> Result<T> response(ResponseEnum responseEnum, String message){
        return Result.<T>toBuilder()
                .message(message)
                .responseEnum(responseEnum)
                .builder();
    }

    /**
     * 得到成功数据体
     * @param <T> 返回数据的类型
     * @return Result
     */
    public static <T> Result<T> success(){
        return Result.<T>toBuilder()
                .code(ResponseEnum.SUCCESS_CODE)
                .builder();
    }

    /**
     * 得到成功数据体
     * @param message 消息内容
     * @param <T> 返回数据的类型
     * @return Result
     */
    public static <T> Result<T> success(String message){
        return Result.<T>toBuilder()
                .code(ResponseEnum.SUCCESS_CODE)
                .message(message)
                .builder();
    }

    /**
     * 得到成功数据体
     * @param data 返回的数据
     * @param <T> 返回数据的类型
     * @return Result
     */
    public static <T> Result<T> success(T data){
        return Result.<T>toBuilder()
                .data(data)
                .code(ResponseEnum.SUCCESS_CODE)
                .builder();
    }

    /**
     * 得到成功数据体
     * @param responseEnum Result 类中的枚举
     * @param data 返回的数据
     * @param <T> 返回数据的类型
     * @return Result
     */
    public static <T> Result<T> success(ResponseEnum responseEnum, T data){
        return Result.<T>toBuilder()
                .data(data)
                .responseEnum(responseEnum)
                .builder();
    }

    /**
     * 得到成功数据体
     * @param responseEnum Result 类中的枚举
     * @param data 返回数据
     * @param message 返回消息提示
     * @param <T> 返回数据的类型
     * @return Result
     */
    public static <T> Result<T> success(ResponseEnum responseEnum, T data, String message){
        return Result.<T>toBuilder()
                .data(data)
                .responseEnum(responseEnum)
                .message(message)
                .builder();
    }

    /**
     * 得到失败数据体
     * @param <T> 返回数据的类型
     * @return Result
     */
    public static <T> Result<T> failure(){
        return Result.<T>toBuilder()
                .code(ResponseEnum.ERROR_CODE)
                .builder();
    }

    /**
     * 得到失败数据体
     * @param message 消息内容
     * @param <T> 返回数据的类型
     * @return Result
     */
    public static <T> Result<T> failure(String message){
        return Result.<T>toBuilder()
                .code(ResponseEnum.ERROR_CODE)
                .message(message)
                .builder();
    }


    /**
     * 得到失败数据体
     * @param responseEnum Result 类中的枚举
     * @param e 异常信息
     * @param <T> 返回数据的类型
     * @return Result
     */
    public static <T> Result<T> failure(ResponseEnum responseEnum, Throwable e){
        String message = getFailureMessage("", e);
        if(StrUtil.isEmpty(message)){
            message = responseEnum.getMessage();
        }
        return Result.<T>toBuilder()
                .responseEnum(responseEnum)
                .message(message)
                .builder();
    }

    /**
     * 得到失败数据体
     * @param responseEnum Result 类中的枚举
     * @param prefixMessage 自定义错误前缀
     * @param e 异常信息
     * @param <T> 返回数据的类型
     * @return Result
     */
    public static <T> Result<T> failure(ResponseEnum responseEnum, String prefixMessage, Throwable e){
        prefixMessage = getFailureMessage(prefixMessage, e);
        return Result.<T>toBuilder()
                .responseEnum(responseEnum)
                .message(prefixMessage)
                .builder();
    }

    /**
     * 转换成Map结构
     * @return Map<String, Object>
     */
    public Map<String, Object> toMap(){
        Map<String, Object> map = Maps.newHashMap();
        map.put("code", code);
        map.put("message", message);
        map.put("data", data);
        return map;
    }

    /**
     * 结果是否为成功
     * @return true 成功, false 不成功
     */
    public boolean isSuccess(){
        return Objects.equals(code, ResponseEnum.SUCCESS_CODE);
    }


    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static <T> Builder<T> toBuilder(){
        return new Builder<T>();
    }

    public static class Builder<T>{

        private Integer code;

        private String message;

        private T data;

        private ResponseEnum response;

        public Builder<T> code(Integer code){
            this.code = code;
            return this;
        }

        public Builder<T> message(String message){
            if(!StringUtils.isEmpty(message)){
                this.message = message;
            }
            return this;
        }

        public Builder<T> data(T data){
            this.data = data;
            return this;
        }

        public Builder<T> responseEnum(ResponseEnum response){
            if (this.code == null) {
                this.code = response.getCode();
            }
            if(this.message == null){
                this.message = response.getMessage();
            }
            return this;
        }

        public Result<T> builder(){
            return new Result<>(code, message, data);
        }
    }


}
