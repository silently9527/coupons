package cn.silently9527.coupons.rest.common.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页参数
 * @author starBlues
 * @version 1.0
 * @since 2020-12-26
 */
@Data
public class PageParam {

    /**
     * 当前页
     */
    @NotNull(message = "currentPage 不能为空")
    @Min(value = 0, message = "当前页不能小于0")
    @ApiModelProperty(value = "当前页码(>=0)", required = true)
    private Integer currentPage;

    /**
     * 页大小
     */
    @NotNull(message = "pageSize 不能为空")
    @Min(value = 1, message = "页大小不能小于1")
    @ApiModelProperty(value = "页大小(>=1)", required = true)
    private Integer pageSize;

}
