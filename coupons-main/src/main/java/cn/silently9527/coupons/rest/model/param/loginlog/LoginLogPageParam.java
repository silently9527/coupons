package cn.silently9527.coupons.rest.model.param.loginlog;

import cn.silently9527.coupons.rest.common.param.PageParam;
import io.swagger.annotations.ApiModel;
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
public class LoginLogPageParam extends PageParam {

    private String username;

    private Integer loginResult;

}
