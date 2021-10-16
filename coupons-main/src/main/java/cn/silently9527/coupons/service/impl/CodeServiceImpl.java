package cn.silently9527.coupons.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import cn.silently9527.coupons.core.exception.BusinessException;
import cn.silently9527.coupons.rest.model.CodeFile;
import cn.silently9527.coupons.service.CodeService;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author silently9527
 * @since 2021-10-16
 */
@Service
public class CodeServiceImpl implements CodeService {
    private final List<String> whitelistFiles = Arrays.asList("package-lock.json", "node_modules");

    @Value("${plugin.clientPath}")
    private String clientPath;

    @Value("${plugin.uploadTempPath}")
    private String tempPath;

    @Override
    public List<CodeFile> files() {
        return listFiles(new File(clientPath));
    }

    private List<CodeFile> listFiles(File dir) {
        File[] files = dir.listFiles((dir1, name) -> !whitelistFiles.contains(name));
        if (Objects.isNull(files)) {
            return new ArrayList<>();
        }
        List<CodeFile> codeFiles = new ArrayList<>();
        for (File file : files) {
            CodeFile codeFile = new CodeFile();
            codeFile.setName(file.getName());
            codeFile.setDir(file.isDirectory());
            codeFile.setPath(file.getAbsolutePath());
            if (file.isDirectory()) {
                codeFile.setChildren(listFiles(file));
            }
            codeFiles.add(codeFile);
        }
        codeFiles.sort(Comparator.comparingInt(file2 -> (file2.isDir() ? -1 : 1)));
        return codeFiles;
    }

    @Override
    public String fileContent(String path) {
        File file = new File(clientPath);
        if (!path.startsWith(file.getAbsolutePath())) {
            throw new BusinessException("只能查看app源代码目录");
        }
        return FileUtil.readString(path, StandardCharsets.UTF_8);
    }

    @Override
    public void saveFile(String path, String content) {
        File file = new File(clientPath);
        if (!path.startsWith(file.getAbsolutePath())) {
            throw new BusinessException("只能查看app源代码目录");
        }

        FileUtil.writeString(content, path, StandardCharsets.UTF_8);
    }

    @Override
    public String packageAppCode() {
        File file = new File(tempPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPath = tempPath + File.separator + "coupons-client.zip";
        ZipUtil.zip(new File(clientPath).getAbsolutePath(), new File(targetPath).getAbsolutePath());
        return targetPath;
    }


}
