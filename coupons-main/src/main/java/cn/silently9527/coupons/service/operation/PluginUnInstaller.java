package cn.silently9527.coupons.service.operation;

import cn.hutool.core.io.FileUtil;
import com.gitee.starblues.integration.IntegrationConfiguration;
import com.gitee.starblues.integration.application.PluginApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

@Slf4j
@Component
public class PluginUnInstaller {
    @Value("${plugin.clientPath}")
    private String clientPath;
    @Resource
    private PluginApplication pluginApplication;

    public boolean uninstall(String pluginCode) {
        try {
            log.info("uninstall plugin:{}", pluginCode);
            if (!pluginApplication.getPluginOperator().uninstall(pluginCode, false)) {
                return false;
            }
            File dir = new File(clientPath + File.separator + "src" + File.separator + "plugins" + File.separator + pluginCode);
            if (dir.exists()) {
                FileUtil.del(dir);
            }
            return true;
        } catch (Exception e) {
            log.error("uninstall plugin fail", e);
            return false;
        }
    }


}
