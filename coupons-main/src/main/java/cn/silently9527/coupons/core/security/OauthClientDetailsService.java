package cn.silently9527.coupons.core.security;

import cn.silently9527.coupons.repository.databases.entity.OauthClientDetails;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.silently9527.coupons.rest.model.param.oauthclient.OauthClientUpdatedParam;

/**
 * <p>
 * 授权客户端表 服务类
 * </p>
 *
 * @author starblues
 * @since 2020-12-31
 */
public interface OauthClientDetailsService extends IService<OauthClientDetails> {

    /**
     * 添加授权客户端
     * @param param 参数
     * @throws Exception 添加异常
     */
    void add(OauthClientUpdatedParam param) throws Exception;

    /**
     * 修改授权客户端
     * @param param 参数
     * @throws Exception 添加异常
     */
    void update(OauthClientUpdatedParam param) throws Exception;

    /**
     * 删除授权客户端
     * @param clientId 客户端id
     */
    void deleteById(String clientId);
}
