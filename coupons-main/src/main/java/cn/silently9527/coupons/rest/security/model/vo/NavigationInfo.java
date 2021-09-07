package cn.silently9527.coupons.rest.security.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author starBlues
 * @version 1.0
 */
@Data
public class NavigationInfo {

    @ApiModelProperty("菜单唯一id")
    private String id;

    @ApiModelProperty("父菜单id")
    private String parentId;

    @ApiModelProperty("菜单唯一key")
    private String key;

    @ApiModelProperty("组件名称")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String component;

    @ApiModelProperty("目录默认重定向的地址")
    private String redirect;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("meta属性")
    private Meta meta;

    @Data
    public static class Meta{

        public static final String TARGET_BLANK = "_blank";

        private String title;

        private Boolean show;

        private String target;

        private String icon;
    }


}
