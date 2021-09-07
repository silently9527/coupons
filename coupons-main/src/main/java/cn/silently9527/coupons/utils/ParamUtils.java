package cn.silently9527.coupons.utils;

import org.springframework.util.StringUtils;

/**
 * 参数工具类
 *
 * @author starBlues
 * @version 1.0
 */
public class ParamUtils {

    private ParamUtils(){}

    /**
     * 检查参数
     * @param paramKey 参数的key。用于异常打印
     * @param value 参数的值
     * @param <T> 参数的类型
     * @return 参数的值
     */
    public static <T> T check(String paramKey, T value){
        if(StringUtils.isEmpty(value)){
            String error = TextUtils.format("Param[{}] can't is empty", paramKey);
            throw new IllegalArgumentException(error);
        }
        return value;
    }

    /**
     * 检查参数
     * @param processId 流程id
     * @param key 参数的标识符。用于异常打印
     * @param value 参数的值
     * @param <T> 参数的类型
     * @return 参数的值
     */
    public static <T> T check(String processId, String key, T value){
        if(StringUtils.isEmpty(value)){
            String error = TextUtils.format("Process[{}] param[{}] can't is empty", processId, key);
            throw new IllegalArgumentException(error);
        }
        return value;
    }


}
