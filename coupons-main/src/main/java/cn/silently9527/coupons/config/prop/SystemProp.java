package cn.silently9527.coupons.config.prop;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 系统配置
 *
 * @author starBlues
 * @version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "system")
public class SystemProp {

    public static final String DEFAULT_JWT_SIGNING_KEY = "defaultSigningKey";

    /**
     * 超级角色的编码
     */
    @Value("${superRoleCode:super_admin}")
    private String superRoleCode;

    /**
     * 超级管理员id
     */
    @Value("${superAdminId:1}")
    private String superAdminId;

    /**
     * jwt 认证的key定义
     */
    @Value("${jwtSigningKey:defaultSigningKey}")
    private String jwtSigningKey = DEFAULT_JWT_SIGNING_KEY;

    /**
     * 是否启用 swagger
     */
    @Value("${swaggerEnable:true}")
    private Boolean swaggerEnable;

    /**
     * 不需要认证的路径
     */
    private Set<String> noAuthPathPatterns;

    /**
     * 文件路径配置
     */
    private FilePathProp filePathProp = new FilePathProp();
}
