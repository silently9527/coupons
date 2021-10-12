package cn.silently9527.coupons.service.operation;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.gitee.starblues.integration.IntegrationConfiguration;
import com.gitee.starblues.integration.application.PluginApplication;
import com.gitee.starblues.integration.operator.PluginOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

@Slf4j
@Component
public class PluginInstaller {
    @Value("${plugin.clientPath}")
    private String clientPath;
    @Resource
    private IntegrationConfiguration integrationConfiguration;
    @Resource
    private PluginApplication pluginApplication;

    public void install(String pluginId, InputStream zipInputStream) {
        try {
            log.info("start install plugin");

            ZipUtil.unzip(zipInputStream, new File(integrationConfiguration.uploadTempPath() + File.separator + pluginId), StandardCharsets.UTF_8);

            installPluginConfigFile(pluginId);
            installPluginJarFile(pluginId);
            installPluginClient(pluginId);
        } catch (Exception ex) {
            //todo: 清理解压后的文件
            log.error("install plugin error", ex);
        }
    }

    private void installPluginClient(String fileName) {
        log.info("install plugin client file");
        File dir = new File(integrationConfiguration.uploadTempPath() + File.separator + fileName + File.separator + "client");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File des = new File(clientPath + File.separator + "src" + File.separator + "plugins");
        FileUtil.copy(dir, des, false);
        new File(des, "client").renameTo(new File(des, fileName));
    }

    private void installPluginJarFile(String fileName) throws Exception {
        log.info("install plugin jar file");
        File dir = new File(integrationConfiguration.uploadTempPath() + File.separator + fileName);
        String[] files = dir.list((dir1, name) -> name.endsWith(".jar"));
        String jarFilePath = files != null && files.length > 0 ? files[0] : null;

        PluginOperator pluginOperator = pluginApplication.getPluginOperator();
        if (jarFilePath != null) {
            pluginOperator.install(Paths.get(dir + File.separator + jarFilePath));
        }
    }

    private void installPluginConfigFile(String fileName) throws Exception {
        log.info("install plugin config file");
        File dir = new File(integrationConfiguration.uploadTempPath() + File.separator + fileName);
        String[] files = dir.list((dir1, name) -> name.endsWith(".yml"));
        String configFilePath = files != null && files.length > 0 ? files[0] : null;

        PluginOperator pluginOperator = pluginApplication.getPluginOperator();
        if (configFilePath != null) {
            pluginOperator.installConfigFile(Paths.get(dir + File.separator + configFilePath));
        }
    }

}
