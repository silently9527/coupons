package cn.silently9527.coupons.rest.model.param.oauthclient;

import cn.silently9527.coupons.rest.common.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页查询参数
 * @author starBlues
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("分页查询参数")
public class OauthClientPageParam extends PageParam {

    @ApiModelProperty(name = "name", value = "名称", required = false)
    private String name;

}
