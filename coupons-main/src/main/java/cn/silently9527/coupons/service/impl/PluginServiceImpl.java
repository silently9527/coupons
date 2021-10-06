package cn.silently9527.coupons.service.impl;

import cn.silently9527.coupons.rest.common.param.PageParam;
import cn.silently9527.coupons.rest.model.PluginDetail;
import cn.silently9527.coupons.service.PluginService;
import cn.silently9527.coupons.service.operation.PluginInstaller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
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
    private RestTemplate restTemplate;

    @Override
    public void installZip(MultipartFile zipFile) throws Exception {
        pluginInstaller.install(zipFile.getInputStream());
    }

    @Override
    public void onlineInstall(String pluginId, String password) {
        try {
            String url = pluginCenterHost + "/api/plugins/plugin-center/mi/plugins/install/" + pluginId + "/" + password;
            ResponseEntity<byte[]> entity = restTemplate.getForEntity(url, byte[].class);
            pluginInstaller.install(new ByteArrayInputStream(Objects.requireNonNull(entity.getBody())));
        } catch (HttpServerErrorException e) {
            log.error("online install fail", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public IPage<PluginDetail> centerList(PageParam param) {
        String url = pluginCenterHost + "/api/plugins/plugin-center/mi/plugins?pageSize=" + param.getPageSize() + "&currentPage=" + param.getCurrentPage();
        String body = restTemplate.getForObject(url, String.class);
        JSONObject result = JSON.parseObject(body).getJSONObject("data");
        Page<PluginDetail> page = new Page<>(result.getLong("current"), result.getLong("size"), result.getLong("total"));
        List<PluginDetail> records = result.getJSONArray("records")
                .stream()
                .map(PluginDetail::new)
                .collect(Collectors.toList());
        page.setRecords(records);
        return page;
    }

}
