package cn.silently9527.coupons.core.security.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.silently9527.coupons.core.exception.BusinessException;
import cn.silently9527.coupons.core.file.FileService;
import cn.silently9527.coupons.core.security.CurrentUserService;
import cn.silently9527.coupons.core.security.LoginLogService;
import cn.silently9527.coupons.core.security.UserService;
import cn.silently9527.coupons.repository.databases.entity.User;
import cn.silently9527.coupons.rest.security.model.param.CurrentUserInfoUpdatedParam;
import cn.silently9527.coupons.utils.AuthUtils;
import cn.silently9527.coupons.utils.CommonUtils;
import cn.silently9527.coupons.utils.IDUtils;
import cn.silently9527.coupons.utils.TimeUtil;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author starBlues
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final FileService fileService;
    private final LoginLogService loginLogService;

    @Override
    public synchronized void updateCurrentUserPassword(String oldPassword, String newPassword) throws Exception {
        Preconditions.checkArgument(!StrUtil.isEmpty(newPassword), "旧密码不能为空");
        Preconditions.checkArgument(!StrUtil.isEmpty(newPassword), "新密码不能为空");
        if(newPassword.length() < 6){
            throw new BusinessException("密码不能小于6个字符");
        }
        String currentUsername = AuthUtils.getCurrentUsername();
        if(StrUtil.isEmpty(currentUsername)){
            throw new BusinessException("没有发现当前认证的用户");
        }
        User databaseUser = userService.getByUsername(currentUsername);
        if(databaseUser == null){
            throw new BusinessException("没有发现用户: " + currentUsername);
        }
        if(!passwordEncoder.matches(oldPassword, databaseUser.getPassword())){
            throw new BusinessException("旧密码错误");
        }
        User updateBean = new User();
        updateBean.setUserId(databaseUser.getUserId());
        updateBean.setPassword(passwordEncoder.encode(newPassword));
        userService.updateById(updateBean);
    }

    @Transactional
    @Override
    public void loginSuccess(String userId, String username, String ip, Long timestamp) {
        User user = new User();
        user.setLastLoginIp(ip);
        user.setLastGmtLoginTime(TimeUtil.formatTime(timestamp));
        user.setUserId(userId);
        userService.updateById(user);
        loginLogService.addSuccess(username, ip, timestamp);
    }

    @Override
    public String updateAvatar(MultipartFile avatarFile) throws Exception {
        String currentUsername = AuthUtils.getCurrentUsername();
        if(StrUtil.isEmpty(currentUsername)){
            throw new BusinessException("当前用户未授权");
        }
        if(avatarFile == null || avatarFile.getSize() <= 0){
            return "";
        }
        String fileNameSuffix = CommonUtils.getFileNameSuffix(avatarFile.getOriginalFilename());
        String fileName = IDUtils.uuid();
        if(!StrUtil.isEmpty(fileNameSuffix)){
            fileName = fileName + "." + fileNameSuffix;
        }
        fileService.saveAvatar(fileName, avatarFile.getInputStream());

        User user = new User();
        user.setAvatar(fileName);
        Wrapper<User> wrapper = Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, currentUsername);
        userService.update(user, wrapper);
        return fileName;
    }

    @Override
    public InputStream getAvatar(String fileName) {
        return fileService.getAvatar(fileName);
    }

    @Override
    public void updateInfo(CurrentUserInfoUpdatedParam param) throws Exception {
        String currentUsername = AuthUtils.getCurrentUsername();
        if(StrUtil.isEmpty(currentUsername)){
            throw new Exception("没有发现当前认证的用户");
        }
        Wrapper<User> wrapper = Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, currentUsername);
        User user = new User();
        BeanUtils.copyProperties(param, user);
        userService.update(user, wrapper);
    }

}
