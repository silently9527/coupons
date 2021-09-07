package cn.silently9527.coupons.utils;

import org.apache.logging.log4j.message.FormattedMessageFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory2;

/**
 * 文字工具类
 *
 * @author starBlues
 * @version 1.0
 */
public class TextUtils {

    private static MessageFactory2 MESSAGE_FACTORY2 = new FormattedMessageFactory();


    private TextUtils(){}


    /**
     * 输出格式化的日志。
     * @param text 格式为 slfj 日志格式化
     * @param params text 里面填充的对象
     * @return 格式化后的 String
     */
    public static String format(String text, Object... params){
        Message message = MESSAGE_FACTORY2.newMessage(text, params);
        return message.getFormattedMessage();
    }


    /**
     * 首字母转大写
     * @param s s
     * @return String
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder())
                    .append(Character.toUpperCase(s.charAt(0)))
                    .append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转小写
     * @param s s
     * @return String
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder())
                    .append(Character.toLowerCase(s.charAt(0)))
                    .append(s.substring(1)).toString();
        }
    }

}
