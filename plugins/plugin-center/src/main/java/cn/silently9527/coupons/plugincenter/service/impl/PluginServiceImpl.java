package cn.silently9527.coupons.plugincenter.service.impl;

import cn.silently9527.coupons.core.exception.BusinessException;
import cn.silently9527.coupons.plugincenter.repository.databases.entity.Plugin;
import cn.silently9527.coupons.plugincenter.repository.databases.mapper.PluginMapper;
import cn.silently9527.coupons.plugincenter.rest.params.AddPluginParam;
import cn.silently9527.coupons.plugincenter.rest.params.UpdatePluginParam;
import cn.silently9527.coupons.plugincenter.service.PluginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PluginServiceImpl extends ServiceImpl<PluginMapper, Plugin> implements PluginService {

    @Override
    public boolean savePlugin(AddPluginParam addPluginParam) {
        Plugin plugin = new Plugin();
        BeanUtils.copyProperties(addPluginParam, plugin);
        plugin.setPassword(String.valueOf((int) ((Math.random() * 9 + 1) * 100000)));
        save(plugin);
        return true;
    }

    @Override
    public void updateStatus(String pluginId, Integer status) {
        Plugin plugin = getById(pluginId);
        plugin.setStatus(status);
        updateById(plugin);
    }

    @Override
    public boolean updatePlugin(UpdatePluginParam updatePluginParam) {
        Plugin plugin = new Plugin();
        BeanUtils.copyProperties(updatePluginParam, plugin);
        return updateById(plugin);
    }

    @Override
    public String validatePluginPassword(String pluginId, String password) {
        Plugin plugin = getById(pluginId);
        if (plugin == null) {
            throw new BusinessException("插件不存在");
        }
        if (!plugin.getPassword().equals(password)) {
            throw new BusinessException("插件提取码错误");
        }
        return plugin.getDownloadUrl();
    }

    @Override
    public void resetPassword() {
        list().forEach(plugin -> {
            plugin.setPassword(String.valueOf((int) ((Math.random() * 9 + 1) * 100000)));
            updateById(plugin);
        });
    }
}
