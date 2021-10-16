package cn.silently9527.coupons.rest;


import cn.silently9527.coupons.repository.databases.entity.Buttons;
import cn.silently9527.coupons.repository.databases.entity.Route;
import cn.silently9527.coupons.rest.common.BaseResource;
import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import cn.silently9527.coupons.rest.model.param.buttons.ButtonsAddParam;
import cn.silently9527.coupons.rest.model.param.buttons.ButtonsPageParam;
import cn.silently9527.coupons.rest.model.param.buttons.ButtonsUpdateParam;
import cn.silently9527.coupons.service.ButtonsService;
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
 * <p>
 * 页面的动态按钮管理 前端控制器
 * </p>
 *
 * @author silently9527
 * @since 2021-10-16
 */
@Slf4j
@RestController
@RequestMapping(BaseResource.API + "buttons")
@Api(tags = "Button接口")
@AllArgsConstructor
public class ButtonsResource {
    private ButtonsService buttonsService;

    @GetMapping()
    @ApiOperation("分页查询")
    public Result<IPage<Buttons>> pageQuery(@Valid ButtonsPageParam param) {
        LambdaQueryWrapper<Buttons> wrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(param.getPage())) {
            wrapper.like(Buttons::getPage, param.getPage());
        }
        if (Objects.nonNull(param.getButtonCode())) {
            wrapper.like(Buttons::getButtonCode, param.getButtonCode());
        }
        Page<Buttons> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        page = buttonsService.page(page, wrapper);
        return success(ApiEnum.GET_SUCCESS, page);
    }

    @PostMapping
    @ApiOperation("添加Buttons")
    public Result<String> add(@RequestBody @Valid ButtonsAddParam param) {
        try {
            buttonsService.addButtons(param);
            return success(ApiEnum.ADD_SUCCESS, "添加成功");
        } catch (Exception e) {
            errorLog(log, e, "添加 '{}' 信息失败 {}", param.getButtonCode(), e.getMessage());
            return failure(ApiEnum.ADD_ERROR, "添加失败.", e);
        }
    }

    @PutMapping
    @ApiOperation("修改Buttons")
    public Result<String> update(@RequestBody @Valid ButtonsUpdateParam param) {
        try {
            buttonsService.updateButtons(param);
            return success(ApiEnum.ADD_SUCCESS, "修改成功");
        } catch (Exception e) {
            errorLog(log, e, "修改 '{}' 信息失败 {}", param.getButtonCode(), e.getMessage());
            return failure(ApiEnum.ADD_ERROR, "修改失败.", e);
        }
    }

    @PutMapping("{buttonsId}/{status}")
    @ApiOperation("修改Buttons状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buttonsId", value = "buttonsId", paramType = "path", required = true),
            @ApiImplicitParam(name = "status", value = "状态（1启用, 0停用）", paramType = "path", required = true)
    })
    public Result<String> updateStatus(@PathVariable("buttonsId") String buttonsId,
                                       @PathVariable("status") Integer status) {
        String message = status == 1 ? "启用" : "禁用";
        try {
            buttonsService.updateStatus(buttonsId, status);
            return response(ApiEnum.UPDATE_SUCCESS, message + "成功");
        } catch (Exception e) {
            errorLog(log, e, "修改状态失败. {}", e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, message + "失败", e);
        }
    }


    @DeleteMapping("/{buttonsId}")
    @ApiOperation("删除Buttons")
    @ApiImplicitParam(name = "buttonsId", value = "buttonsId", paramType = "path", required = true)
    public Result<String> delete(@PathVariable("buttonsId") String buttonsId) {
        try {
            buttonsService.removeById(buttonsId);
            return response(ApiEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            errorLog(log, e, "删除失败. {}", e.getMessage());
            return failure(ApiEnum.DELETE_ERROR, "删除失败", e);
        }
    }
}

