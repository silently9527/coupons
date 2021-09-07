package cn.silently9527.coupons.rest;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.silently9527.coupons.core.security.OauthClientDetailsService;
import cn.silently9527.coupons.repository.databases.entity.OauthClientDetails;
import cn.silently9527.coupons.rest.common.BaseResource;
import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import cn.silently9527.coupons.rest.model.param.oauthclient.OauthClientPageParam;
import cn.silently9527.coupons.rest.model.param.oauthclient.OauthClientUpdatedParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import static cn.silently9527.coupons.rest.common.Result.*;
import static cn.silently9527.coupons.rest.common.ResultUtils.errorLog;

/**
 * 授权客户端接口配置
 * @author starBlues
 * @version 1.0
 */
@RestController
@RequestMapping(BaseResource.API + "oauth-client")
@Api(tags = "授权客户端接口")
@AllArgsConstructor
@Slf4j
public class OauthClientResource extends BaseResource {

    private final OauthClientDetailsService oauthClientService;


    @GetMapping()
    @PreAuthorize("@auth.permission('oauthClient:query')")
    @ApiOperation("分页查询授权客户端")
    public Result<IPage<OauthClientDetails>> pageQuery(@Valid OauthClientPageParam param) {
        LambdaQueryWrapper<OauthClientDetails> wrapper = Wrappers.<OauthClientDetails>lambdaQuery();
        if(!StrUtil.isEmpty(param.getName())){
            wrapper.like(OauthClientDetails::getName, param.getName());
        }
        wrapper.orderByDesc(OauthClientDetails::getGmtCreated);
        Page<OauthClientDetails> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        page = oauthClientService.page(page, wrapper);
        return success(ApiEnum.GET_SUCCESS, page);
    }

    @PostMapping
    @PreAuthorize("@auth.permission('oauthClient:add')")
    @ApiOperation("添加授权客户端")
    public Result<String> add(@RequestBody @Valid OauthClientUpdatedParam param){
        try {
            String clientSecret = param.getClientSecret();
            if(StrUtil.isEmpty(clientSecret)){
                return response(ApiEnum.ADD_ERROR, "秘钥不能为空");
            }
            oauthClientService.add(param);
            return success(ApiEnum.ADD_SUCCESS, "添加成功");
        } catch (Exception e) {
            errorLog(log, e, "添加授权客户端 '{}' 失败 {}", param.getClientId(), e.getMessage());
            return failure(ApiEnum.ADD_ERROR, "添加失败", e);
        }
    }

    @PutMapping
    @PreAuthorize("@auth.permission('oauthClient:update')")
    @ApiOperation("修改授权客户端")
    public Result<String> update(@RequestBody @Valid OauthClientUpdatedParam param){
        try {
            oauthClientService.update(param);
            return response(ApiEnum.UPDATE_SUCCESS, "更新成功");
        } catch (Exception e) {
            errorLog(log, e, "更新授权客户端 '{}' 失败 {}", param.getClientId(), e.getMessage());
            return failure(ApiEnum.UPDATE_ERROR, "更新失败", e);
        }
    }

    @DeleteMapping("/{clientId}")
    @PreAuthorize("@auth.permission('oauthClient:delete')")
    @ApiOperation("删除授权客户端")
    @ApiImplicitParam(name = "clientId", value = "授权客户端id", paramType = "path", required = true)
    public Result<String> delete(@PathVariable("clientId") String clientId){
        oauthClientService.deleteById(clientId);
        return response(ApiEnum.DELETE_SUCCESS);
    }

}
