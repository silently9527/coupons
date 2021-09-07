package cn.silently9527.coupons.rest.common;

import cn.hutool.core.util.StrUtil;
import cn.silently9527.coupons.core.exception.BusinessException;
import org.slf4j.Logger;

/**
 * 结果工具
 * @author starBlues
 * @version 1.0
 */
public class ResultUtils {

    /**
     * 是否为被定义的异常
     * @param throwable 异常
     * @return 被定义返回 true
     */
    public static boolean isThrowableDefined(Throwable throwable){
        if(throwable instanceof BusinessException){
            return true;
        }
        if(throwable instanceof IllegalArgumentException){
            return true;
        }
        return false;
    }

    /**
     * 异常打印
     * @param logger 日志打印对象
     * @param e 异常信息, 如果为被定义的异常, 则不进行异常栈打印
     * @param format 日志内容
     * @param arguments 日志填充值
     */
    public static void errorLog(Logger logger, Throwable e, String format,  Object... arguments){
        if(isThrowableDefined(e)){
            logger.error(format, arguments);
        } else {
            logger.error(format, arguments, e);
        }
    }

    /**
     * 得到错误信息
     * @param prefixMessage 信息前缀
     * @param e 异常
     * @return 错误信息
     */
    public static String getFailureMessage(String prefixMessage, Throwable e){
        if(isThrowableDefined(e)){
            String m = e.getMessage();
            if(StrUtil.isEmpty(m)){
                return prefixMessage;
            } else {
                return prefixMessage + ": " + m;
            }
        }
        return prefixMessage;
    }

}
