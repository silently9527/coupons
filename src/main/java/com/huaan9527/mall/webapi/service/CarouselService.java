package com.huaan9527.mall.webapi.service;

import com.huaan9527.mall.webapi.domain.Carousel;
import com.huaan9527.mall.webapi.repository.CarouselRepository;
import com.huaan9527.mall.webapi.vos.CarouselVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CarouselService {

    private CarouselRepository carouselRepository;


    public List<CarouselVo> list() {
        List<Carousel> carouselList = carouselRepository.findByStatusOrderByCreatedDateDesc(1);
        return carouselList.stream().map(carousel -> {
            CarouselVo vo = new CarouselVo();
            BeanUtils.copyProperties(carousel, vo);
            vo.setId(carousel.getId());
            return vo;
        }).collect(Collectors.toList());
    }


}
