package cn.silently9527.coupons.rest;


import cn.silently9527.coupons.repository.databases.entity.Config;
import cn.silently9527.coupons.repository.databases.entity.TabBar;
import cn.silently9527.coupons.rest.common.BaseResource;
import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import cn.silently9527.coupons.rest.model.param.tabbar.TabBarAddParam;
import cn.silently9527.coupons.rest.model.param.tabbar.TabBarPageParam;
import cn.silently9527.coupons.rest.model.param.tabbar.TabBarUpdateParam;
import cn.silently9527.coupons.service.ConfigService;
import cn.silently9527.coupons.service.TabBarService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

import static cn.silently9527.coupons.rest.common.Result.*;
import static cn.silently9527.coupons.rest.common.ResultUtils.errorLog;

/**
 * @author silently9527
 * @since 2021-09-26
 */
@Slf4j
@RestController
@RequestMapping(BaseResource.API + "config")
@Api(tags = "Config接口")
@AllArgsConstructor
public class ConfigResource {

    private ConfigService configService;

    @GetMapping()
    @ApiOperation("根据类型查询配置")
    public Result<Config> queryByConfigType(@RequestParam(name = "configType") String configType) {
        return success(ApiEnum.GET_SUCCESS, configService.queryByConfigType(configType));
    }

    @PostMapping
    @ApiOperation("Update配置")
    public Result<String> update(@RequestParam(name = "configType") String configType, @RequestParam(name = "textJson") String textJson) {
        try {
            configService.update(configType, textJson);
            return success(ApiEnum.ADD_SUCCESS, "保存成功");
        } catch (Exception e) {
            errorLog(log, e, "保存失败 {}", e.getMessage());
            return failure(ApiEnum.ADD_ERROR, "保存失败.", e);
        }
    }

}

