package cn.silently9527.coupons.rest.model.param.carousel;

import cn.silently9527.coupons.rest.common.param.PageParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("查询参数")
public class CarouselPageParam extends PageParam {

}
