package cn.silently9527.coupons.config.security;

import cn.hutool.core.util.StrUtil;
import cn.silently9527.coupons.config.prop.SystemProp;
import cn.silently9527.coupons.utils.AuthUtils;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 权限校验实现
 * @author starBlues
 * @version 1.0
 */
@Service("auth")
@Slf4j
@AllArgsConstructor
public class PreAuthorizeService {

    public static final String SUPPER_PERMISSION = "*:*";


    private final SystemProp systemProp;

    public boolean permission(String... permissions){
        if(permissions == null || permissions.length == 0){
            return false;
        }

        Set<String> authoritySet = AuthUtils.getAuthoritySet();
        if(authoritySet.isEmpty()){
            return false;
        }
        if(authoritySet.contains(SUPPER_PERMISSION)){
            return true;
        }
        for (String permission : permissions) {
            if(StrUtil.isEmpty(permission)){
                continue;
            }
            if(authoritySet.contains(permission)){
                return true;
            }
        }
        return false;
    }

    public boolean role(String... roleCodes){
        if(roleCodes == null || roleCodes.length == 0){
            return false;
        }
        Set<String> roleCodeSet = Sets.newHashSet(roleCodes);
        String superRoleCode = systemProp.getSuperRoleCode();
        if(roleCodeSet.contains(superRoleCode)){
            return true;
        }

        Set<String> currentRoleCodes = AuthUtils.getCurrentRoleCodes();
        if(currentRoleCodes.isEmpty()){
            return false;
        }
        for (String roleCode : currentRoleCodes) {
            if(StrUtil.isEmpty(roleCode)){
                continue;
            }
            if(currentRoleCodes.contains(roleCode)){
                return true;
            }
        }
        return false;
    }

}
