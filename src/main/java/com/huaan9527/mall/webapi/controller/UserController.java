package com.huaan9527.mall.webapi.controller;

import com.huaan9527.mall.webapi.service.UserService;
import com.huaan9527.mall.webapi.utils.ResponseEntity;
import com.huaan9527.mall.webapi.vos.SendEmailCodeVo;
import com.huaan9527.mall.webapi.vos.UpdatePasswordVo;
import com.huaan9527.mall.webapi.vos.RegisterVo;
import com.huaan9527.mall.webapi.vos.UserVo;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;


    @GetMapping("/user/current_user")
    public ResponseEntity getCurrentUser() {
        UserVo userVo = userService.getCurrentUser();
        return ResponseEntity.success(userVo);
    }

    @PostMapping("/user/init_current_user")
    public ResponseEntity initCurrentUser(UserVo userVo) {
        userService.initUser(userVo);
        return ResponseEntity.success(userService.getCurrentUser());
    }

    @PostMapping("/user/update")
    public ResponseEntity update(UserVo userVo) {
        userService.update(userVo);
        UserVo user = userService.getCurrentUser();
        return ResponseEntity.success(user);
    }

}
