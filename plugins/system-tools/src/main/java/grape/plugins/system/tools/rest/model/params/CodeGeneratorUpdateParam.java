package grape.plugins.system.tools.rest.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author starBlues
 * @version 1.0
 */
@Data
@ApiModel("代码生成更新配置参数")
public class CodeGeneratorUpdateParam {


    /**
     * id
     */
    @ApiModelProperty(value = "id", required = true)
    @NotEmpty(message = "id不能为空")
    private String id;


    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    @NotEmpty(message = "名称不能为空")
    private String name;


    /**
     * 驱动名称
     */
    @ApiModelProperty(value = "驱动名称", required = true)
    @NotEmpty(message = "驱动名称不能为空")
    private String driverName;

    /**
     * url地址
     */
    @ApiModelProperty(value = "url", required = true)
    @NotEmpty(message = "url不能为空")
    private String url;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message = "密码不能为空")
    private String password;

    /**
     * 生成的基本包名
     */
    @ApiModelProperty(value = "生成的基本包名", required = true)
    @NotEmpty(message = "生成的基本包名不能为空")
    private String basePackageName;

    /**
     * 生成的表名(全部生成则为*, 多个用逗号分隔)
     */
    @ApiModelProperty(value = "生成的表名(全部生成则为*, 多个用逗号分隔)", required = true)
    @NotEmpty(message = "生成的基本包名不能为空")
    private String tableNames;

    /**
     * 作者名称
     */
    @ApiModelProperty(value = "作者名称")
    private String author;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

}
