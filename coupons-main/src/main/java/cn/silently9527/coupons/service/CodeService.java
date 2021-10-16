package cn.silently9527.coupons.service;

import cn.silently9527.coupons.rest.model.CodeFile;

import java.util.List;

/**
 * @author silently9527
 * @since 2021-10-16
 */
public interface CodeService {

    List<CodeFile> files();

    String fileContent(String path);

    void saveFile(String path, String content);

    String packageAppCode();
}
