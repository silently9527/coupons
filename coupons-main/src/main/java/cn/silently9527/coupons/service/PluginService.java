package cn.silently9527.coupons.service;

import org.springframework.web.multipart.MultipartFile;

public interface PluginService {
    boolean installZip(MultipartFile zipFile) throws Exception;
}
