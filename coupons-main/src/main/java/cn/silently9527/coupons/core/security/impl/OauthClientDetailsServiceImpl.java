package cn.silently9527.coupons.core.security.impl;

import cn.hutool.core.util.StrUtil;
import cn.silently9527.coupons.core.exception.BusinessException;
import cn.silently9527.coupons.repository.databases.entity.OauthClientDetails;
import cn.silently9527.coupons.repository.databases.mapper.OauthClientDetailsMapper;
import cn.silently9527.coupons.core.security.OauthClientDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.silently9527.coupons.rest.model.param.oauthclient.OauthClientUpdatedParam;
import com.google.common.base.Joiner;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 授权客户端表 服务实现类
 * </p>
 *
 * @author starblues
 * @since 2020-12-25
 */
@Service
@AllArgsConstructor
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails>
        implements OauthClientDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public void add(OauthClientUpdatedParam param) throws Exception {
        synchronized(this){
            OauthClientDetails oauthClientDetails = getById(param.getClientId());
            if(oauthClientDetails != null){
                throw new BusinessException("已经存在授权客户端: " + param.getClientId());
            }
            Set<String> authorizedGrantTypes = param.getAuthorizedGrantTypes();
            if(authorizedGrantTypes == null || authorizedGrantTypes.isEmpty()){
                throw new BusinessException("授权类型不能为空");
            }
            oauthClientDetails = new OauthClientDetails();
            BeanUtils.copyProperties(param, oauthClientDetails);
            String authorizedGrantTypesJoin = Joiner.on(",").skipNulls().join(authorizedGrantTypes);
            oauthClientDetails.setAuthorizedGrantTypes(authorizedGrantTypesJoin);
            oauthClientDetails.setClientSecret(passwordEncoder.encode(param.getClientSecret()));
            save(oauthClientDetails);
        }
    }

    @Override
    public void update(OauthClientUpdatedParam param) throws Exception {
        Set<String> authorizedGrantTypes = param.getAuthorizedGrantTypes();
        if(authorizedGrantTypes == null || authorizedGrantTypes.isEmpty()){
            throw new BusinessException("授权类型不能为空");
        }
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        BeanUtils.copyProperties(param, oauthClientDetails);
        String authorizedGrantTypesJoin = Joiner.on(",").skipNulls().join(authorizedGrantTypes);
        oauthClientDetails.setAuthorizedGrantTypes(authorizedGrantTypesJoin);
        String clientSecret = param.getClientSecret();
        if(StrUtil.isEmpty(clientSecret)){
            oauthClientDetails.setClientSecret(null);
        } else {
            oauthClientDetails.setClientSecret(passwordEncoder.encode(clientSecret));
        }
        updateById(oauthClientDetails);
    }

    @Override
    public synchronized void deleteById(String clientId) {
        synchronized(this){
            removeById(clientId);
        }
    }


}
