package grape.plugins.system.tools.service.impl;

import grape.plugins.system.tools.config.SystemToolsConfig;
import grape.plugins.system.tools.entity.CodeGeneratorInfo;
import grape.plugins.system.tools.mapper.CodeGeneratorInfoMapper;
import grape.plugins.system.tools.service.CodeGeneratorInfoService;
import grape.plugins.system.tools.utils.CodeGenerator;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.gitee.starblues.grape.utils.DownloadUtils;
import com.gitee.starblues.extension.mybatis.mybatisplus.ServiceImplWrapper;
import com.google.common.base.Splitter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>
 * 代码生成者信息 服务实现类
 * </p>
 *
 * @author starblues
 * @since 2021-01-11
 */
@Service
public class CodeGeneratorInfoServiceImpl extends ServiceImplWrapper<CodeGeneratorInfoMapper, CodeGeneratorInfo>
        implements CodeGeneratorInfoService {

    private final SystemToolsConfig config;
    private AtomicBoolean isGen = new AtomicBoolean(false);

    public CodeGeneratorInfoServiceImpl(SystemToolsConfig config,
                                        CodeGeneratorInfoMapper mapper) {
        super(mapper);
        this.config = config;
    }

    @Override
    public void gen(String id, HttpServletResponse response) throws Exception {
        if(isGen.get()){
            throw new Exception("正在生成中, 不能重复提交");
        }
        isGen.set(true);
        try {
            CodeGeneratorInfo info = getById(id);
            if(info == null){
                throw new Exception("没有发现该配置: " + id);
            }

            String outputDirPath = config.getCodeGeneratorDir() + File.separator + info.getName();

            File outputDirFile = new File(outputDirPath);
            if(!outputDirFile.exists()){
                FileUtil.mkdir(outputDirPath);
            } else {
                FileUtil.del(outputDirPath);
                FileUtil.mkdir(outputDirPath);
            }

            CodeGenerator codeGenerator = new CodeGenerator(
                    info.getDriverName(), info.getUrl(), info.getUsername(), info.getPassword(),
                    DbType.MYSQL, info.getAuthor(), outputDirPath
            );
            String tableNames = info.getTableNames();
            if(Objects.equals(tableNames, "*")){
                codeGenerator.generateAll(info.getBasePackageName(), false);
            } else {
                List<String> strings = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(tableNames);
                codeGenerator.generateByTables(info.getBasePackageName(), false, strings.toArray(new String[]{}));
            }

            File zip = ZipUtil.zip(outputDirFile);
            DownloadUtils.downloadFile(response, zip, zip.getName());
        } finally {
            isGen.set(false);
        }
    }
}
