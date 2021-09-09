package grape.plugins.system.tools.rest.model.params;

import cn.silently9527.coupons.rest.common.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author starBlues
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("代码生成参数")
public class CodeGeneratorPageParam extends PageParam {


    @ApiModelProperty(name = "name", value = "名称")
    private String name;

}
