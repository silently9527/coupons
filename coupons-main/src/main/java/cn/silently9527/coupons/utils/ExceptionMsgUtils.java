package cn.silently9527.coupons.utils;

import org.springframework.util.StringUtils;

/**
 * 异常信息输出工具类
 *
 * @author starBlues
 * @version 1.0
 */
public class ExceptionMsgUtils {


    private ExceptionMsgUtils(){}

    public static Exception getInputParamException(String type, String paramKey, String errorMsg){
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("'").append(type).append("' ")
                .append("input param");
        if(!StringUtils.isEmpty(paramKey)){
            stringBuffer.append(":").append(paramKey);
        }
        stringBuffer.append("  ").append(errorMsg);
        return new Exception(stringBuffer.toString());
    }

    public static Exception getInputParamException(String type, String paramKey){
        return getInputParamException(type, paramKey, "can't be null");
    }



}
