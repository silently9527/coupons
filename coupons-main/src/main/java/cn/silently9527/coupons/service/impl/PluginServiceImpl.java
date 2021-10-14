package cn.silently9527.coupons.service.impl;

import cn.silently9527.coupons.rest.common.param.PageParam;
import cn.silently9527.coupons.rest.model.PluginDetail;
import cn.silently9527.coupons.service.PluginService;
import cn.silently9527.coupons.service.operation.PluginInstaller;
import cn.silently9527.coupons.service.operation.PluginUnInstaller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gitee.starblues.integration.application.PluginApplication;
import com.gitee.starblues.integration.operator.PluginOperator;
import com.gitee.starblues.integration.operator.module.PluginInfo;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.Plugin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PluginServiceImpl implements PluginService {

    @Value("${plugin.centerHost}")
    private String pluginCenterHost;
    @Resource
    private PluginInstaller pluginInstaller;
    @Resource
    private PluginUnInstaller pluginUnInstaller;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private PluginApplication pluginApplication;

    @Override
    public void installZip(MultipartFile zipFile) throws Exception {
        String pluginId = Objects.requireNonNull(zipFile.getOriginalFilename())
                .substring(0, zipFile.getOriginalFilename().lastIndexOf("."));
        pluginInstaller.install(pluginId, zipFile.getInputStream());
    }

    @Override
    public void onlineInstall(String pluginId, String pluginCode, String password) {
        try {
            String url = pluginCenterHost + "/api/plugins/plugin-center/mi/plugins/install/" + pluginId + "/" + password;
            ResponseEntity<byte[]> entity = restTemplate.getForEntity(url, byte[].class);
            pluginInstaller.install(pluginCode, new ByteArrayInputStream(Objects.requireNonNull(entity.getBody())));
        } catch (HttpServerErrorException e) {
            log.error("online install fail", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean uninstall(String pluginCode) {
        return pluginUnInstaller.uninstall(pluginCode);
    }

    @Override
    public IPage<PluginDetail> centerList(PageParam param) {
        String url = pluginCenterHost + "/api/plugins/plugin-center/mi/plugins?pageSize=" + param.getPageSize() + "&currentPage=" + param.getCurrentPage();
        String body = restTemplate.getForObject(url, String.class);
        JSONObject result = JSON.parseObject(body).getJSONObject("data");
        Page<PluginDetail> page = new Page<>(result.getLong("current"), result.getLong("size"), result.getLong("total"));
        PluginOperator pluginOperator = pluginApplication.getPluginOperator();
        List<PluginInfo> pluginInfos = pluginOperator.getPluginInfo();
        List<PluginDetail> records = result.getJSONArray("records")
                .stream()
                .map(o -> {
                    PluginDetail pluginDetail = new PluginDetail(o);
                    pluginInfos.stream()
                            .filter(pluginInfo -> pluginInfo.getPluginDescriptor().getPluginId().equals(pluginDetail.getPluginCode()))
                            .findFirst()
                            .ifPresent(info -> pluginDetail.setInstalled(true));
                    return pluginDetail;
                })
                .collect(Collectors.toList());
        page.setRecords(records);
        return page;
    }

}
