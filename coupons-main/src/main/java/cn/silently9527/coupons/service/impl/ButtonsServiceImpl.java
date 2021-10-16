package cn.silently9527.coupons.service.impl;

import cn.silently9527.coupons.core.security.UserService;
import cn.silently9527.coupons.core.security.model.AuthUserInfo;
import cn.silently9527.coupons.repository.databases.entity.Buttons;
import cn.silently9527.coupons.repository.databases.mapper.ButtonsMapper;
import cn.silently9527.coupons.rest.model.param.buttons.ButtonsAddParam;
import cn.silently9527.coupons.rest.model.param.buttons.ButtonsUpdateParam;
import cn.silently9527.coupons.service.ButtonsService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 页面的动态按钮管理 服务实现类
 * </p>
 *
 * @author silently9527
 * @since 2021-10-16
 */
@Service
@AllArgsConstructor
public class ButtonsServiceImpl extends ServiceImpl<ButtonsMapper, Buttons> implements ButtonsService {
    private UserService userService;

    @Override
    public void addButtons(ButtonsAddParam param) {
        Buttons buttons = new Buttons();
        BeanUtils.copyProperties(param, buttons);

        AuthUserInfo authUserInfo = userService.getAuthUserInfo();
        buttons.setUserId(authUserInfo.getUserId());

        save(buttons);
    }

    @Override
    public void updateButtons(ButtonsUpdateParam param) {
        Buttons buttons = new Buttons();
        BeanUtils.copyProperties(param, buttons);

        AuthUserInfo authUserInfo = userService.getAuthUserInfo();
        buttons.setUserId(authUserInfo.getUserId());

        updateById(buttons);
    }

    @Override
    public void updateStatus(String buttonsId, Integer status) {
        Wrapper<Buttons> wrapper = Wrappers.<Buttons>lambdaQuery().eq(Buttons::getId, buttonsId);
        Buttons buttons = new Buttons();
        buttons.setStatus(status);
        update(buttons, wrapper);
    }


}
