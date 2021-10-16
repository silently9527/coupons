package cn.silently9527.coupons.service.impl;

import cn.silently9527.coupons.core.security.UserService;
import cn.silently9527.coupons.core.security.model.AuthUserInfo;
import cn.silently9527.coupons.repository.databases.entity.Carousel;
import cn.silently9527.coupons.repository.databases.mapper.CarouselMapper;
import cn.silently9527.coupons.rest.model.param.carousel.CarouselAddParam;
import cn.silently9527.coupons.rest.model.param.carousel.CarouselUpdateParam;
import cn.silently9527.coupons.service.CarouselService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 轮播图 服务实现类
 * </p>
 *
 * @author silently9527
 * @since 2021-10-16
 */
@Service
@AllArgsConstructor
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {
    private UserService userService;

    @Override
    public void addCarousel(CarouselAddParam param) {
        Carousel carousel = new Carousel();
        BeanUtils.copyProperties(param, carousel);

        AuthUserInfo authUserInfo = userService.getAuthUserInfo();
        carousel.setUserId(authUserInfo.getUserId());

        save(carousel);
    }

    @Override
    public void updateCarousel(CarouselUpdateParam param) {
        Carousel carousel = new Carousel();
        BeanUtils.copyProperties(param, carousel);

        AuthUserInfo authUserInfo = userService.getAuthUserInfo();
        carousel.setUserId(authUserInfo.getUserId());

        updateById(carousel);
    }

    @Override
    public void updateStatus(String carouselId, Integer status) {
        Wrapper<Carousel> wrapper = Wrappers.<Carousel>lambdaQuery().eq(Carousel::getId, carouselId);
        Carousel carousel = new Carousel();
        carousel.setStatus(status);
        update(carousel, wrapper);
    }
}
