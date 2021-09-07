package cn.silently9527.coupons.utils;

import com.gitee.starblues.integration.user.PluginUser;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * 公用操作工具类
 *
 * @author starBlues
 * @version 1.0
 */
public class CommonUtils {

    private CommonUtils(){

    }

    /**
     * 得到系统中满足条件的接口的实现类
     * @param pluginUser 插件使用者
     * @param interfaceClass 接口类
     * @param function 函数式判断
     * @param <T> 泛型
     * @return 得到实现类
     */
    public static <T> T getImpls(PluginUser pluginUser,
                                 Class<T> interfaceClass,
                                 Function<T, Boolean> function) {
        List<T> impls = pluginUser.getBeans(interfaceClass);
        if(impls == null || impls.isEmpty()){
            return null;
        }
        for (T impl : impls) {
            if(impl == null){
                continue;
            }
            if(function.apply(impl)){
                return impl;
            }
        }
        return null;
    }





    /**
     * 得到要初始化的参数
     * @param params 参数
     * @param paramClass 参数bean
     * @return 参数bean实例
     */
    public static Object getParam(Gson gson, Map<String, Object> params, Class paramClass){
        if(params == null){
            return null;
        }
        if(Converter.isBasicDataType(paramClass)){
            // String 类型
            Object first = getFirst(params);
            if(first == null){
                return null;
            }
            return Converter.cast(first, paramClass);
        }

        String json = gson.toJson(params);
        return gson.fromJson(json, paramClass);
    }

    /**
     * 得到泛型的类型的数组
     * @param aClass 抽象类
     * @return 类型数组
     */
    public static Type[] getParameterizedType(Class aClass){
        if(aClass == null){
            return null;
        }
        Type type = aClass.getGenericSuperclass();
        Type[] params = ((ParameterizedType) type).getActualTypeArguments();
        return params;
    }



    /**
     * 生成uuid
     * @return uuid
     */
    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 得到第一个参数的值
     * @param params 参数
     * @return 第一个参数的值
     */
    private static Object getFirst(Map<String, Object> params){
        if(params == null){
            return null;
        }
        for (String key : params.keySet()) {
            return params.get(key);
        }
        return null;
    }

    /**
     * 得到文件名称后缀
     * @param originalFilename 文件名称
     * @return 后缀
     */
    public static String getFileNameSuffix(String originalFilename){
        if(originalFilename == null){
            return "";
        }
        if(originalFilename.lastIndexOf(".")!=-1) {
            return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        return "";
    }

}
