package cn.silently9527.coupons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 插件日志工厂
 * @author starBlues
 * @version 1.0
 */
public class PluginLogger {

    private PluginLogger(){}


    public static Logger getLogger(Class<?> aClass){
        return LoggerFactory.getLogger(aClass);
    }

    public static Logger getLogger(Object object){
        return LoggerFactory.getLogger(object.getClass());
    }


}
