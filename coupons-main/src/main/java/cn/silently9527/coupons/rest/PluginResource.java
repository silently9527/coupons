package cn.silently9527.coupons.rest;

import cn.silently9527.coupons.rest.common.BaseResource;
import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import cn.silently9527.coupons.rest.common.param.PageParam;
import cn.silently9527.coupons.rest.model.PluginDetail;
import cn.silently9527.coupons.service.PluginService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gitee.starblues.integration.application.PluginApplication;
import com.gitee.starblues.integration.operator.PluginOperator;
import com.gitee.starblues.integration.operator.module.PluginInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static cn.silently9527.coupons.rest.common.Result.*;

/**
 * 插件接口
 *
 * @author starBlues
 * @version 1.0
 */
@RestController
@RequestMapping(BaseResource.API + "plugin")
@Api(tags = "菜单接口")
@AllArgsConstructor
@Slf4j
public class PluginResource extends BaseResource {
    private PluginService pluginService;
    private final PluginApplication pluginApplication;

    @GetMapping()
    @PreAuthorize("@auth.permission('plugin:query')")
    @ApiOperation("获取插件列表")
    public Result<List<PluginInfo>> getPluginList() {
        PluginOperator pluginOperator = pluginApplication.getPluginOperator();
        return success(ApiEnum.GET_SUCCESS, pluginOperator.getPluginInfo());
    }

    /**
     * 上传Zip并安装插件。注意: 该操作只适用于生产环境
     *
     * @return 操作结果
     */
    @PostMapping("/zip-install")
    @PreAuthorize("@auth.permission('plugin:operate')")
    @ApiOperation("安装Zip插件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pluginZipFile", value = "插件zip文件", paramType = "from", required = true)
    })
    public Result<String> installZip(@RequestParam("pluginZipFile") MultipartFile zipFile) {
        try {
            if (zipFile == null || zipFile.getSize() <= 0) {
                return response(ApiEnum.OPERATE_ERROR, "安装失败, zip 包为空");
            }
            if (!Objects.requireNonNull(zipFile.getOriginalFilename()).endsWith("zip")) {
                return response(ApiEnum.OPERATE_ERROR, "上传的文件必须是zip");
            }

            pluginService.installZip(zipFile);
            return response(ApiEnum.OPERATE_SUCCESS, "安装并启动成功");
        } catch (Exception e) {
            log.error("安装插件失败.", e);
            return response(ApiEnum.OPERATE_ERROR, "安装插件失败." + e.getMessage());
        }
    }

    @ApiOperation("在线安装插件")
    @PostMapping("/online-install/{pluginId}/{pluginCode}/{password}")
    public Result<String> onlineInstall(@PathVariable("pluginId") String pluginId,
                                        @PathVariable("pluginCode") String pluginCode,
                                        @PathVariable("password") String password) {
        try {
            pluginService.onlineInstall(pluginId,pluginCode ,password);
            return response(ApiEnum.OPERATE_SUCCESS, "安装并启动成功");
        } catch (Exception e) {
            log.error("安装插件失败.", e);
            return response(ApiEnum.OPERATE_ERROR, "安装插件失败." + e.getMessage());
        }
    }


    /**
     * 根据插件id卸载插件
     *
     * @param id 插件id
     * @return 返回操作结果
     */
    @PostMapping("/uninstall/{id}")
    @PreAuthorize("@auth.permission('plugin:operate')")
    @ApiOperation("卸载插件")
    @ApiImplicitParam(name = "id", value = "插件id", paramType = "path", required = true)
    public Result<String> uninstall(@PathVariable("id") String id) {
        try {
            PluginOperator pluginOperator = pluginApplication.getPluginOperator();
            if (pluginOperator.uninstall(id, true)) {
                return response(ApiEnum.OPERATE_SUCCESS, "卸载成功");
            } else {
                return response(ApiEnum.OPERATE_ERROR, "卸载失败");
            }
        } catch (Exception e) {
            log.error("卸载插件 '{}' 失败.", id, e);
            return response(ApiEnum.OPERATE_ERROR, "卸载插件失败." + e.getMessage());
        }
    }

    @GetMapping("/center/list")
    @ApiOperation("插件中心的list")
    public Result<IPage<PluginDetail>> centerList(PageParam param) {
        try {
            IPage<PluginDetail> pluginDetails = pluginService.centerList(param);
            return success(pluginDetails);
        } catch (Exception e) {
            log.error("从插件中心获取插件列表失败", e);
            return response(ApiEnum.OPERATE_ERROR, "获取插件失败." + e.getMessage());
        }
    }


    //- 过期方法 ---------

    /**
     * 根据插件id启动插件
     *
     * @param id 插件id
     * @return 返回操作结果
     */
    @Deprecated
    @PostMapping("/start/{id}")
    @PreAuthorize("@auth.permission('plugin:operate')")
    @ApiOperation("启动插件")
    @ApiImplicitParam(name = "id", value = "插件id", paramType = "path", required = true)
    public Result<String> start(@PathVariable("id") String id) {
        try {
            PluginOperator pluginOperator = pluginApplication.getPluginOperator();
            if (pluginOperator.start(id)) {
                return response(ApiEnum.OPERATE_SUCCESS, "启动成功");
            } else {
                return response(ApiEnum.OPERATE_ERROR, "启动失败");
            }
        } catch (Exception e) {
            log.error("启动插件 '{}' 失败.", id, e);
            return response(ApiEnum.OPERATE_ERROR, "启动失败." + e.getMessage());
        }
    }

    /**
     * 根据插件id停止插件
     *
     * @param id 插件id
     * @return 返回操作结果
     */
    @Deprecated
    @PostMapping("/stop/{id}")
    @PreAuthorize("@auth.permission('plugin:operate')")
    @ApiOperation("停止插件")
    @ApiImplicitParam(name = "id", value = "插件id", paramType = "path", required = true)
    public Result<String> stop(@PathVariable("id") String id) {
        try {
            PluginOperator pluginOperator = pluginApplication.getPluginOperator();
            if (pluginOperator.stop(id)) {
                return response(ApiEnum.OPERATE_SUCCESS, "停止成功");
            } else {
                return response(ApiEnum.OPERATE_ERROR, "停止失败");
            }
        } catch (Exception e) {
            log.error("停止插件 '{}' 失败.", id, e);
            return response(ApiEnum.OPERATE_ERROR, "停止失败." + e.getMessage());
        }
    }

    /**
     * 上传并安装插件。注意: 该操作只适用于生产环境
     *
     * @param jarFile    插件jar文件
     * @param configFile 插件配置文件
     * @return 操作结果
     */
    @Deprecated
    @PostMapping("/install")
    @PreAuthorize("@auth.permission('plugin:operate')")
    @ApiOperation("安装插件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pluginJarFile", value = "插件jar包文件", paramType = "from", required = true),
            @ApiImplicitParam(name = "pluginConfigFile", value = "插件包对应的配置文件(如果存在则上传)", paramType = "from")
    })
    public Result<String> install(@RequestParam("pluginJarFile") MultipartFile jarFile,
                                  @RequestParam(value = "pluginConfigFile", required = false) MultipartFile configFile) {
        try {
            PluginOperator pluginOperator = pluginApplication.getPluginOperator();
            if (configFile != null && configFile.getSize() > 0) {
                pluginOperator.uploadConfigFile(configFile);
            }
            if (jarFile != null && jarFile.getSize() > 0) {
                if (pluginOperator.uploadPluginAndStart(jarFile)) {
                    return response(ApiEnum.OPERATE_SUCCESS, "安装并启动成功");
                } else {
                    return response(ApiEnum.OPERATE_ERROR, "安装失败");
                }
            }
            return response(ApiEnum.OPERATE_ERROR, "安装失败, jar 包为空");
        } catch (Exception e) {
            log.error("安装插件失败.", e);
            return response(ApiEnum.OPERATE_ERROR, "安装插件失败." + e.getMessage());
        }
    }

}
