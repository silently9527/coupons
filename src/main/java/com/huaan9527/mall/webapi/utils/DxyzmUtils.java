package com.huaan9527.mall.webapi.utils;

import java.security.SecureRandom;
import java.util.Random;

public class DxyzmUtils {
    private static final String SYMBOLS = "0123456789"; // 数字

    // 字符串
    // private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final Random RANDOM = new SecureRandom();

    /**
     * 获取长度为 6 的随机数字
     * @return 随机数字
     */
    public static String getNonceStr() {
        // 如果需要4位，那 new char[4] 即可，其他位数同理可得
        char[] nonceChars = new char[6];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

}
