package cn.silently9527.coupons.service;

import cn.silently9527.coupons.rest.common.param.PageParam;
import cn.silently9527.coupons.rest.model.PluginDetail;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PluginService {
    void installZip(MultipartFile zipFile) throws Exception;

    IPage<PluginDetail> centerList(PageParam param);

    void onlineInstall(String pluginId, String password);
}
