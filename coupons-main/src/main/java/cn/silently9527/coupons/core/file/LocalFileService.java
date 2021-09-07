package cn.silently9527.coupons.core.file;

import cn.hutool.core.io.FileUtil;
import cn.silently9527.coupons.config.prop.FilePathProp;
import cn.silently9527.coupons.config.prop.SystemProp;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

/**
 * 本地文件服务
 * @author starBlues
 * @version 1.0
 */
@Service
@Primary
public class LocalFileService implements FileService{

    private final static String DEFAULT_AVATAR = "/avatar/default-avatar.png";

    private final FilePathProp filePathProp;

    public LocalFileService(SystemProp systemProp) {
        this.filePathProp = systemProp.getFilePathProp();
    }

    @Override
    public void saveAvatar(String fileName, InputStream inputStream) {
        String userAvatarPath = filePathProp.getUserAvatarPath();
        fileName = userAvatarPath + File.separator + fileName;
        FileUtil.writeFromStream(inputStream, fileName);
    }

    @Override
    public InputStream getAvatar(String avatarFileName) {
        String userAvatarPath = filePathProp.getUserAvatarPath();
        avatarFileName = userAvatarPath + File.separator + avatarFileName;
        if(FileUtil.exist(avatarFileName)){
            return FileUtil.getInputStream(avatarFileName);
        } else {
            return this.getClass().getResourceAsStream(DEFAULT_AVATAR);
        }
    }
}
