package cn.silently9527.coupons.plugincenter.service;

import cn.silently9527.coupons.plugincenter.repository.databases.entity.Plugin;
import cn.silently9527.coupons.plugincenter.rest.params.AddPluginParam;
import cn.silently9527.coupons.plugincenter.rest.params.UpdatePluginParam;
import com.baomidou.mybatisplus.extension.service.IService;

public interface PluginService extends IService<Plugin> {
    boolean savePlugin(AddPluginParam addPluginParam);

    void updateStatus(String pluginId, Integer status);

    boolean updatePlugin(UpdatePluginParam updatePluginParam);

    /**
     * 校验通过返回文件路径
     * @param pluginId
     * @param password
     * @return
     */
    String validatePluginPassword(String pluginId, String password);
}
