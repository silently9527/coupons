package com.huaan9527.mall.webapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huaan9527.mall.webapi.client.MobServiceClient;
import com.huaan9527.mall.webapi.service.GoodsService;
import com.huaan9527.mall.webapi.service.UserService;
import com.huaan9527.mall.webapi.utils.CodeGenerator;
import com.huaan9527.mall.webapi.vos.SendEmailCodeVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MallWebapiApplication.class)
@ActiveProfiles("dev")
public class MallWebapiApplicationTests {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private MailSender mailSender;
    @Autowired
    private UserService userService;
    @Autowired
    private MobServiceClient mobServiceClient;

    @Test
    public void recommendGoods() {
        JSONObject jsonObject = goodsService.recommendGoods(1, 50);
        System.out.println(JSON.toJSONString(jsonObject));
    }

    @Test
    public void test() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("380303318@qq.com");
        message.setTo("18380483688@139.com");
        message.setSubject("烂尾楼发完了");
        message.setText("违法违规");
        mailSender.send(message);
    }

    @Test
    public void test1() {
        System.out.println(CodeGenerator.random(true, 6));
    }

    @Test
    public void test2() {
        SendEmailCodeVo vo = new SendEmailCodeVo();
        vo.setEmail("18380483688@139.com");
        vo.setMobile("18380483688");
        userService.sendEmailCode(vo);
    }


    @Test
    public void test3() {
        Boolean aBoolean = mobServiceClient.confirmSMSCode("18380483688", "7275");
        System.out.println(aBoolean);
    }


}
