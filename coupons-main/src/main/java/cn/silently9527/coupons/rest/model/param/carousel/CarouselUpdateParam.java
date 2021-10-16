package cn.silently9527.coupons.rest.model.param.carousel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("修改Carousel参数")
public class CarouselUpdateParam {
    @ApiModelProperty(name = "id", value = "id", required = true)
    @NotNull(message = "id不能为空")
    private String id;

    @ApiModelProperty(name = "title", value = "标题", required = true)
    @NotNull(message = "标题不能为空")
    private String title;

    @ApiModelProperty(name = "image", value = "图片", required = true)
    @NotNull(message = "图片不能为空")
    private String image;

    @ApiModelProperty(name = "url", value = "url", required = true)
    @NotNull(message = "url不能为空")
    private String url;

    @ApiModelProperty(name = "urlType", value = "urlType", required = true)
    @NotNull(message = "url类型不能为空")
    private String urlType;

    @ApiModelProperty(name = "sorted", value = "排序", required = true)
    @NotNull(message = "排序不能为空")
    private Integer sorted;

}
