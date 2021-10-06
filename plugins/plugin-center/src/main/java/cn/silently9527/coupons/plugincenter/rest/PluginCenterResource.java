package cn.silently9527.coupons.plugincenter.rest;

import cn.hutool.core.io.FileUtil;
import cn.silently9527.coupons.plugincenter.repository.databases.entity.Plugin;
import cn.silently9527.coupons.plugincenter.rest.model.PluginDetail;
import cn.silently9527.coupons.plugincenter.rest.params.AddPluginParam;
import cn.silently9527.coupons.plugincenter.rest.params.UpdatePluginParam;
import cn.silently9527.coupons.plugincenter.service.PluginService;
import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import cn.silently9527.coupons.rest.common.param.PageParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static cn.silently9527.coupons.rest.common.Result.*;
import static cn.silently9527.coupons.rest.common.ResultUtils.errorLog;

@RestController
@Api(tags = "插件中心")
@RequestMapping("")
@AllArgsConstructor
@Slf4j
public class PluginCenterResource {

    private PluginService pluginService;

    @GetMapping(value = {"/plugins"})
    @ApiOperation("分页条件查询插件列表")
    public Result<IPage<PluginDetail>> pageQuery(@Valid PageParam param) {
        QueryWrapper<Plugin> queryWrapper = Wrappers.query();
        queryWrapper.orderByDesc("gmt_created");
        Page<Plugin> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        page = pluginService.page(page, queryWrapper);

        Page<PluginDetail> data = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        data.setRecords(page.getRecords().stream().map(PluginDetail::new).collect(Collectors.toList()));
        return success(ApiEnum.GET_SUCCESS, data);
    }

    @GetMapping(value = {"/mi/plugins"})
    @ApiOperation("openApi分页条件查询插件列表")
    public Result<IPage<PluginDetail>> publicPageQuery(@Valid PageParam param) {
        QueryWrapper<Plugin> queryWrapper = Wrappers.query();
        queryWrapper.orderByDesc("gmt_created");
        queryWrapper.eq("status", 1);
        Page<Plugin> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        page = pluginService.page(page, queryWrapper);

        Page<PluginDetail> data = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        data.setRecords(page.getRecords().stream().map(PluginDetail::openAPIPluginDetail).collect(Collectors.toList()));
        return success(ApiEnum.GET_SUCCESS, data);
    }


    @PostMapping("/plugins")
    @ApiOperation("保存插件")
    public Result<String> add(@RequestBody @Valid AddPluginParam addPluginParam) {
        boolean result = pluginService.savePlugin(addPluginParam);
        if (result) {
            return success("新增成功");
        }
        return failure("新增失败");
    }

    @PutMapping("/plugins")
    @ApiOperation("修改插件")
    public Result<String> update(@RequestBody @Valid UpdatePluginParam updatePluginParam) {
        boolean result = pluginService.updatePlugin(updatePluginParam);
        if (result) {
            return success("修改成功");
        }
        return failure("修改失败");
    }

    @PutMapping("/plugins/{pluginId}/{status}")
    @ApiOperation("修改Plugin状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pluginId", value = "pluginId", paramType = "path", required = true),
            @ApiImplicitParam(name = "status", value = "状态（1启用, 0停用）", paramType = "path", required = true)
    })
    public Result<String> updateStatus(@PathVariable("pluginId") String pluginId,
                                       @PathVariable("status") Integer status) {
        String message = status == 1 ? "启用" : "禁用";
        try {
            pluginService.updateStatus(pluginId, status);
            return response(ApiEnum.UPDATE_SUCCESS, message + "成功");
        } catch (Exception e) {
            errorLog(log, e, "修改状态失败. {}", e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, message + "失败", e);
        }
    }

    @DeleteMapping("/plugins/{pluginId}")
    @ApiOperation("删除Plugin")
    @ApiImplicitParam(name = "pluginId", value = "pluginId", paramType = "path", required = true)
    public Result<String> delete(@PathVariable("pluginId") String pluginId) {
        try {
            pluginService.removeById(pluginId);
            return response(ApiEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            errorLog(log, e, "删除失败. {}", e.getMessage());
            return failure(ApiEnum.DELETE_ERROR, "删除失败", e);
        }
    }

    @GetMapping("/mi/plugins/install/{pluginId}/{password}")
    @ApiOperation("安装插件")
    public ResponseEntity<byte[]> installPlugin(@PathVariable("pluginId") String pluginId,
                                                @PathVariable("password") String password) {
        try {
            String path = pluginService.validatePluginPassword(pluginId, password);
            byte[] bytes = FileUtil.readBytes(new File(path));
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Disposition", "attachment;filename=" + pluginId + ".zip");
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("下载出错:", e);
            return new ResponseEntity<>(e.getMessage().getBytes(StandardCharsets.UTF_8), HttpStatus.INSUFFICIENT_STORAGE);
        }
    }

}
