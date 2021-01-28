package com.huaan9527.mall.webapi.controller;

import com.huaan9527.mall.webapi.vos.EventVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/mi/event")
public class EventController {

    @PostMapping("/collect")
    public void collectEvent(@RequestBody EventVo eventVo) {
        log.error("collectEvent.eventVo=>{}", eventVo.toString());
    }

}
