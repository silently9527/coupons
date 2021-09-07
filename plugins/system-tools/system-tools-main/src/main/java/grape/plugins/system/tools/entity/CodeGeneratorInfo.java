package grape.plugins.system.tools.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 代码生成者信息
 * </p>
 *
 * @author starblues
 * @since 2021-01-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CodeGeneratorInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 名称
     */
    private String name;


    /**
     * 驱动名称
     */
    private String driverName;

    /**
     * url地址
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 生成的基本包名
     */
    private String basePackageName;

    /**
     * 生成的表名(全部生成则为*, 多个用逗号分隔)
     */
    private String tableNames;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String gmtCreated;

    /**
     * 作者名称
     */
    private String author;

    /**
     * 描述
     */
    private String description;

}
