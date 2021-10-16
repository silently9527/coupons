package cn.silently9527.coupons.rest;


import cn.silently9527.coupons.repository.databases.entity.Carousel;
import cn.silently9527.coupons.rest.common.BaseResource;
import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import cn.silently9527.coupons.rest.model.param.carousel.CarouselAddParam;
import cn.silently9527.coupons.rest.model.param.carousel.CarouselPageParam;
import cn.silently9527.coupons.rest.model.param.carousel.CarouselUpdateParam;
import cn.silently9527.coupons.service.CarouselService;
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

import static cn.silently9527.coupons.rest.common.Result.*;
import static cn.silently9527.coupons.rest.common.ResultUtils.errorLog;

/**
 * <p>
 * 轮播图 前端控制器
 * </p>
 *
 * @author silently9527
 * @since 2021-10-16
 */
@Slf4j
@RestController
@RequestMapping(BaseResource.API + "carousel")
@Api(tags = "Carousel接口")
@AllArgsConstructor
public class CarouselResource {
    private CarouselService carouselService;

    @GetMapping()
    @ApiOperation("分页查询")
    public Result<IPage<Carousel>> pageQuery(@Valid CarouselPageParam param) {
        LambdaQueryWrapper<Carousel> wrapper = Wrappers.lambdaQuery();
        Page<Carousel> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        page = carouselService.page(page, wrapper);
        return success(ApiEnum.GET_SUCCESS, page);
    }

    @PostMapping
    @ApiOperation("添加Carousel")
    public Result<String> add(@RequestBody @Valid CarouselAddParam param) {
        try {
            carouselService.addCarousel(param);
            return success(ApiEnum.ADD_SUCCESS, "添加成功");
        } catch (Exception e) {
            errorLog(log, e, "添加 '{}' 信息失败 {}", param.getUrl(), e.getMessage());
            return failure(ApiEnum.ADD_ERROR, "添加失败.", e);
        }
    }

    @PutMapping
    @ApiOperation("修改Carousel")
    public Result<String> update(@RequestBody @Valid CarouselUpdateParam param) {
        try {
            carouselService.updateCarousel(param);
            return success(ApiEnum.ADD_SUCCESS, "修改成功");
        } catch (Exception e) {
            errorLog(log, e, "修改 '{}' 信息失败 {}", param.getUrl(), e.getMessage());
            return failure(ApiEnum.ADD_ERROR, "修改失败.", e);
        }
    }

    @PutMapping("{carouselId}/{status}")
    @ApiOperation("修改carousel状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "carouselId", value = "carouselId", paramType = "path", required = true),
            @ApiImplicitParam(name = "status", value = "状态（1启用, 0停用）", paramType = "path", required = true)
    })
    public Result<String> updateStatus(@PathVariable("carouselId") String carouselId,
                                       @PathVariable("status") Integer status) {
        String message = status == 1 ? "启用" : "禁用";
        try {
            carouselService.updateStatus(carouselId, status);
            return response(ApiEnum.UPDATE_SUCCESS, message + "成功");
        } catch (Exception e) {
            errorLog(log, e, "修改状态失败. {}", e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, message + "失败", e);
        }
    }


    @DeleteMapping("/{carouselId}")
    @ApiOperation("删除carousel")
    @ApiImplicitParam(name = "carouselId", value = "carouselId", paramType = "path", required = true)
    public Result<String> delete(@PathVariable("carouselId") String carouselId) {
        try {
            carouselService.removeById(carouselId);
            return response(ApiEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            errorLog(log, e, "删除失败. {}", e.getMessage());
            return failure(ApiEnum.DELETE_ERROR, "删除失败", e);
        }
    }

}

