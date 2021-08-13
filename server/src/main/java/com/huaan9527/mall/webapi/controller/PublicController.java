package com.huaan9527.mall.webapi.controller;

import com.huaan9527.mall.webapi.service.CarouselService;
import com.huaan9527.mall.webapi.service.MenuService;
import com.huaan9527.mall.webapi.service.UserService;
import com.huaan9527.mall.webapi.utils.DateUtils;
import com.huaan9527.mall.webapi.utils.MD5Util;
import com.huaan9527.mall.webapi.utils.ResponseEntity;
import com.huaan9527.mall.webapi.utils.WebUtils;
import com.huaan9527.mall.webapi.vos.RegisterVo;
import com.huaan9527.mall.webapi.vos.RegisterVo2;
import com.huaan9527.mall.webapi.vos.SendEmailCodeVo;
import com.huaan9527.mall.webapi.vos.UpdatePasswordVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/mi")
public class PublicController {
    private UserService userService;
    private CarouselService carouselService;
    private MenuService menuService;
    @Resource
    private RedisTemplate redisTemplate;

    @Deprecated
    @PostMapping("/register")
    public ResponseEntity register(@Valid RegisterVo registerVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.failure(result.getFieldErrors().get(0).getDefaultMessage());
        }
        userService.register(registerVo);
        return ResponseEntity.success("注册成功");
    }

    @PostMapping("/register2")
    public ResponseEntity register2(@Valid RegisterVo2 registerVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.failure(result.getFieldErrors().get(0).getDefaultMessage());
        }
        Base64.Encoder encoder = Base64.getEncoder();
        String orginalStr = "mobile=" + registerVo.getMobile() + "&inviteCode=" + registerVo.getInviteCode()
                + "&verifyCode="+registerVo.getVerifyCode();
        String validSign = MD5Util.getMD5(encoder.encodeToString(orginalStr.getBytes()));
        if (!registerVo.getSign().equals(validSign)) {
            log.error("register2=>签名校验失败");
            return ResponseEntity.failure("签名校验失败");
        }

        userService.register2(registerVo);
        return ResponseEntity.success("注册成功");
    }

    @Deprecated
    @PostMapping("/update_password")
    public ResponseEntity updatePassword(@Valid UpdatePasswordVo updatePasswordVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.failure(result.getFieldErrors().get(0).getDefaultMessage());
        }
        userService.updatePassword(updatePasswordVo);
        return ResponseEntity.success("密码更新成功");
    }

    @Deprecated
    @PostMapping("/send_email_code")
    public ResponseEntity sendEmailCode(@Valid SendEmailCodeVo sendEmailCodeVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.failure(result.getFieldErrors().get(0).getDefaultMessage());
        }
        userService.sendEmailCode(sendEmailCodeVo);
        return ResponseEntity.success("验证码已发送到邮箱");
    }


    /**
     * md5(base64("mobile="+mobile.value+"&type=register"))
     * 一个手机号一天最多5次
     * 一个ip一天15
     *
     * @return
     */
    @PostMapping("/send_mobile_code")
    public ResponseEntity sendMobileCode(String mobile, String type, String sign, HttpServletRequest request) {
        Base64.Encoder encoder = Base64.getEncoder();
        String orginalStr = "mobile=" + mobile + "&type=" + type;
        String validSign = MD5Util.getMD5(encoder.encodeToString(orginalStr.getBytes()));
        if (!sign.equals(validSign)) {
            log.error("sendMobileCode=>签名校验失败");
            return ResponseEntity.failure("签名校验失败");
        }

        ValueOperations<String, Integer> opsForValue = redisTemplate.opsForValue();
        String mobileKey = "mobile_code_count_" + mobile;
        Integer redisValue = opsForValue.get(mobileKey);
        int mobileCount = Objects.isNull(redisValue) ? 0 : redisValue;
        if (mobileCount > 5) {
            log.error("手机号『{}』今日已达发送上限！", mobile);
            return ResponseEntity.failure("您今日已达发送上限");
        }

        String clientIp = WebUtils.clientIp(request);
        String ipKey = "ip_code_count_" + clientIp;
        redisValue = opsForValue.get(ipKey);
        int ipCount = Objects.isNull(redisValue) ? 0 : redisValue;
        if (ipCount > 15) {
            log.error("IP『{}』今日已达发送上限！", clientIp);
            return ResponseEntity.failure("您今日已达发送上限");
        }

        Date endTime = DateUtils.getEndTime();
        opsForValue.set(mobileKey, mobileCount + 1);
        opsForValue.set(ipKey, ipCount + 1);
        redisTemplate.expireAt(mobileKey, endTime);
        redisTemplate.expireAt(ipKey, endTime);

        userService.sendMobileCode(mobile, type);
        return ResponseEntity.success("验证码发送成功");
    }


    @PostMapping("/list_carousel")
    public ResponseEntity listCarousel() {
        return ResponseEntity.success(carouselService.list());
    }

    @PostMapping("/list_menus")
    public ResponseEntity listMenus() {
        return ResponseEntity.success(menuService.list());
    }

}
