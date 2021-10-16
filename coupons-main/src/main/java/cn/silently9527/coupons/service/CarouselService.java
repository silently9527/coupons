package cn.silently9527.coupons.service;

import cn.silently9527.coupons.repository.databases.entity.Carousel;
import cn.silently9527.coupons.rest.model.param.carousel.CarouselAddParam;
import cn.silently9527.coupons.rest.model.param.carousel.CarouselUpdateParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 轮播图 服务类
 * </p>
 *
 * @author silently9527
 * @since 2021-10-16
 */
public interface CarouselService extends IService<Carousel> {

    void addCarousel(CarouselAddParam param);

    void updateCarousel(CarouselUpdateParam param);

    void updateStatus(String carouselId, Integer status);
}
