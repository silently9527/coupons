package cn.silently9527.coupons.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import cn.silently9527.coupons.service.PluginService;
import com.gitee.starblues.integration.IntegrationConfiguration;
import com.gitee.starblues.integration.application.PluginApplication;
import com.gitee.starblues.integration.operator.PluginOperator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class PluginServiceImpl implements PluginService {
    @Value("${plugin.clientPath}")
    private String clientPath;
    @Resource
    private IntegrationConfiguration integrationConfiguration;
    @Resource
    private PluginApplication pluginApplication;

    @Override
    public boolean installZip(MultipartFile zipFile) throws Exception {
        ZipUtil.unzip(zipFile.getInputStream(), new File(integrationConfiguration.uploadTempPath()), StandardCharsets.UTF_8);
        String fileName = this.getFileNameNotWithExt(Objects.requireNonNull(zipFile.getOriginalFilename()));

        installPluginConfigFile(fileName);
        installPluginJarFile(fileName);
        installPluginClient(fileName);
        //todo: 触发生成pages.json   manifest.json的事件

        return false;
    }

    private void installPluginClient(String fileName) {
        File dir = new File(integrationConfiguration.uploadTempPath() + File.separator + fileName + File.separator + "client");
        File des = new File(clientPath + File.separator + "plugins");
        FileUtil.copy(dir, des, false);
        new File(des, "client").renameTo(new File(des, fileName));
    }

    private void installPluginJarFile(String fileName) throws Exception {
        File dir = new File(integrationConfiguration.uploadTempPath() + File.separator + fileName);
        String[] files = dir.list((dir1, name) -> name.endsWith(".jar"));
        String jarFilePath = files != null && files.length > 0 ? files[0] : null;

        PluginOperator pluginOperator = pluginApplication.getPluginOperator();
        if (jarFilePath != null) {
            pluginOperator.install(Paths.get(dir + File.separator + jarFilePath));
        }
    }

    private void installPluginConfigFile(String fileName) throws Exception {
        File dir = new File(integrationConfiguration.uploadTempPath() + File.separator + fileName + File.separator + "config");
        String[] files = dir.list((dir1, name) -> name.endsWith(".yml"));
        String configFilePath = files != null && files.length > 0 ? files[0] : null;

        PluginOperator pluginOperator = pluginApplication.getPluginOperator();
        if (configFilePath != null) {
            pluginOperator.installConfigFile(Paths.get(dir + File.separator + configFilePath));
        }
    }

    private String getFileNameNotWithExt(String originalFilename) {
        return originalFilename.substring(0, originalFilename.indexOf("."));
    }


}
