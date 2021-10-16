package cn.silently9527.coupons.repository.databases.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 页面的动态按钮管理
 * </p>
 *
 * @author silently9527
 * @since 2021-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Buttons implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 创建用户
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String gmtCreated;

    /**
     * 修改用户
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String modifiedUser;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String gmtModified;


    /**
     * 用户id
     */
    private String userId;

    /**
     * 所属的页面名字
     */
    private String page;

    /**
     * 编码
     */
    private String buttonCode;

    /**
     * 按钮文字
     */
    private String text;

    /**
     * 图标的类型：image、icon
     */
    private String iconType;

    /**
     * image
     */
    private String image;
    /**
     * url
     */
    private String url;

    /**
     * URL类型
     */
    private String urlType;

    /**
     * 状态（1启用, 0停用）
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sorted;

    /**
     * 备注
     */
    private String remark;


}
