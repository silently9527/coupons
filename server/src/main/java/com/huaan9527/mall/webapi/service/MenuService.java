package com.huaan9527.mall.webapi.service;

import com.huaan9527.mall.webapi.domain.Menu;
import com.huaan9527.mall.webapi.repository.MenuRepository;
import com.huaan9527.mall.webapi.vos.MenuVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class MenuService {
    private MenuRepository menuRepository;

    public List<MenuVo> list() {
        List<Menu> menus = menuRepository.findByStatusOrderBySortAsc(1);
        return menus.stream().map(menu -> {
            MenuVo vo = new MenuVo();
            BeanUtils.copyProperties(menu, vo);
            vo.setId(menu.getId());
            return vo;
        }).collect(Collectors.toList());
    }
}
