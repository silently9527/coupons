package com.huaan9527.mall.webapi.service;

import com.huaan9527.mall.webapi.domain.User;
import com.huaan9527.mall.webapi.domain.enums.UserType;
import com.huaan9527.mall.webapi.exception.MsException;
import com.huaan9527.mall.webapi.repository.UserRepository;
import com.huaan9527.mall.webapi.security.SecurityUtils;
import com.huaan9527.mall.webapi.sms.SmsProvider;
import com.huaan9527.mall.webapi.utils.CodeGenerator;
import com.huaan9527.mall.webapi.utils.Constants;
import com.huaan9527.mall.webapi.utils.DxyzmUtils;
import com.huaan9527.mall.webapi.vos.RegisterVo;
import com.huaan9527.mall.webapi.vos.RegisterVo2;
import com.huaan9527.mall.webapi.vos.SendEmailCodeVo;
import com.huaan9527.mall.webapi.vos.UpdatePasswordVo;
import com.huaan9527.mall.webapi.vos.UserVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private static final String DEFAULT_PASSWORD = "123456";
    private UserRepository userRepository;
    private MailSender mailSender;
    private SmsProvider smsProvider;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 注册用户
     *
     * @return
     */
    @Transactional
    public User signUp(Connection<?> connection) {
        User user = new User();
        user.signUpFromConnection(connection);
        user.setUserType(UserType.Visitor.name());
        userRepository.save(user);
        return user;
    }

    @Transactional
    public User update(UserVo userVo) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        user.setId(currentUserId);
        userRepository.updateIgnoreNull(user);
        return user;
    }

    public UserVo getCurrentUser() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.getById(currentUserId);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        userVo.setUserId(currentUserId);
        return userVo;
    }

    @Transactional
    public User initUser(UserVo userVo) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        user.setId(currentUserId);
        user.setUserType(UserType.Register.name());
        userRepository.updateIgnoreNull(user);
        return user;
    }

    public User findByMobile(String mobile) {
        return userRepository.findByMobile(mobile);
    }

    @Transactional
    public void register(RegisterVo registerVo) {
        String mobile = registerVo.getMobile();
        if (userRepository.countByMobile(mobile) > 0) {
            throw new MsException("User", "手机号已经存在");
        }

        String email = registerVo.getEmail();
        if (userRepository.countByEmail(mobile) > 0) {
            throw new MsException("User", "邮箱已经存在");
        }

        User user = new User();
        user.setUserType(UserType.Register.name());
        user.setAccount(mobile);
        user.setNick(mobile);
        user.setMobile(mobile);
        user.setEmail(email);
        user.setPassword(registerVo.getPassword());
        userRepository.save(user);
    }

    @Transactional
    public User register(String mobile) {
        if (userRepository.countByMobile(mobile) > 0) {
            throw new MsException("User", "手机号已经存在");
        }

        User user = new User();
        user.setUserType(UserType.Register.name());
        user.setAccount(mobile);
        user.setNick(mobile);
        user.setMobile(mobile);
        user.setPassword(DEFAULT_PASSWORD);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void register2(RegisterVo2 registerVo) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String cachedCode = opsForValue.get("MobileCode_" + registerVo.getMobile());
        if (registerVo.getVerifyCode().equals(cachedCode)) {
            throw new MsException("User", "验证码有误");
        }
    }


    @SuppressWarnings("unchecked")
    public void sendEmailCode(SendEmailCodeVo vo) {
        User user = userRepository.findByMobile(vo.getMobile());
        if (!vo.getEmail().equals(user.getEmail())) {
            throw new MsException("SendMail", "该邮箱与注册预留邮箱不一致");
        }

        String code = CodeGenerator.random(true, 6);
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set(Constants.EMAIL_CODE_CACHE_KEY + user.getMobile(), code, 30, TimeUnit.MINUTES);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("380303318@qq.com");
        message.setTo(user.getEmail());
        message.setSubject("邮箱验证码");
        message.setText("验证码:" + code);

        mailSender.send(message);
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public void updatePassword(UpdatePasswordVo vo) {
        ValueOperations ops = redisTemplate.opsForValue();
        String key = Constants.EMAIL_CODE_CACHE_KEY + vo.getMobile();
        Object o = ops.get(key);
        if (o == null) {
            throw new MsException("UpdatePassword", "验证码已失效");
        }
        String code = o.toString();
        if (!code.equals(vo.getCode())) {
            throw new MsException("UpdatePassword", "验证码错误");
        }

        User user = userRepository.findByMobile(vo.getMobile());
        user.setPassword(vo.getNewPassword());
        userRepository.save(user);

        redisTemplate.delete(key);
    }

    public void sendMobileCode(String mobile, String type) {
        if (userRepository.countByMobile(mobile) > 0) {
            throw new MsException("User", "手机号已经注册");
        }

        String code = DxyzmUtils.getNonceStr();
        log.info("sendMobileCode, mobile:[{}], code:[{}]", mobile, code);

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("MobileCode_" + mobile, code, 30, TimeUnit.MINUTES);


        //发送手机验证码
//        SendSmsRequest request = new SendSmsRequest();
//        smsProvider.sendSms()
    }

}
