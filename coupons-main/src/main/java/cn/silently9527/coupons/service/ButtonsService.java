package cn.silently9527.coupons.service;

import cn.silently9527.coupons.repository.databases.entity.Buttons;
import cn.silently9527.coupons.rest.model.param.buttons.ButtonsAddParam;
import cn.silently9527.coupons.rest.model.param.buttons.ButtonsUpdateParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 页面的动态按钮管理 服务类
 * </p>
 *
 * @author silently9527
 * @since 2021-10-16
 */
public interface ButtonsService extends IService<Buttons> {

    void addButtons(ButtonsAddParam param);

    void updateButtons(ButtonsUpdateParam param);

    void updateStatus(String buttonsId, Integer status);
}
