package grape.plugins.system.tools.service;

import grape.plugins.system.tools.entity.CodeGeneratorInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 代码生成者信息 服务类
 * </p>
 *
 * @author starblues
 * @since 2021-01-11
 */
public interface CodeGeneratorInfoService extends IService<CodeGeneratorInfo> {

    /**
     * 生成代码
     * @param id id
     * @param response 响应
     * @throws Exception 异常
     */
    void gen(String id, HttpServletResponse response) throws Exception;


}
