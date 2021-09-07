package cn.silently9527.coupons.config.security;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author starBlues
 * @version 1.0
 */
public class CompositePasswordEncoder implements PasswordEncoder {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public static String getMd5(String rawPassword){
        if(StrUtil.isEmpty(rawPassword)){
            return rawPassword;
        }
        return DigestUtils.md5Hex(rawPassword);
    }

}
