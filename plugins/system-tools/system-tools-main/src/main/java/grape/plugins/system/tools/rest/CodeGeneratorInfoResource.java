package grape.plugins.system.tools.rest;

import grape.plugins.system.tools.config.SystemToolsConfig;
import grape.plugins.system.tools.entity.CodeGeneratorInfo;
import grape.plugins.system.tools.rest.model.params.CodeGeneratorPageParam;
import grape.plugins.system.tools.rest.model.params.CodeGeneratorUpdateParam;
import grape.plugins.system.tools.service.CodeGeneratorInfoService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gitee.starblues.grape.rest.common.BaseResource;
import com.gitee.starblues.grape.rest.common.Result;
import com.gitee.starblues.grape.rest.common.enums.ApiEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import static com.gitee.starblues.grape.rest.common.Result.*;

/**
 * @author starBlues
 * @version 1.0
 */
@RestController
@RequestMapping("/code-generator")
@Api(tags = "代码生成接口")
@AllArgsConstructor
@Slf4j
public class CodeGeneratorInfoResource extends BaseResource {

    private final CodeGeneratorInfoService generatorInfoService;
    private final SystemToolsConfig config;

    @GetMapping()
    @ApiOperation("分页条件查询代码生成配置")
    public Result<IPage<CodeGeneratorInfo>> pageQuery(@Valid CodeGeneratorPageParam param) {
        QueryWrapper<CodeGeneratorInfo> queryWrapper = Wrappers.query();
        if(!StrUtil.isEmpty(param.getName())){
            queryWrapper.like("name", param.getName());
        }
        queryWrapper.orderByDesc("gmt_created");
        Page<CodeGeneratorInfo> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        page = generatorInfoService.page(page, queryWrapper);
        return success(ApiEnum.GET_SUCCESS, page);
    }

    @PostMapping()
    @ApiOperation("新增配置")
    public Result<String> add(@RequestBody @Valid CodeGeneratorUpdateParam param) {
        CodeGeneratorInfo codeGeneratorInfo = new CodeGeneratorInfo();
        BeanUtils.copyProperties(param, codeGeneratorInfo);
        boolean result = generatorInfoService.save(codeGeneratorInfo);
        if(result){
            return success("新增配置成功");
        } else {
            return failure("新增配置失败");
        }
    }

    @PostMapping("/gen/{id}")
    @ApiOperation("生成代码")
    public void gen(@PathVariable("id") String id,
                    HttpServletResponse response) throws Exception {
        generatorInfoService.gen(id, response);
    }

    @PutMapping()
    @ApiOperation("修改配置")
    public Result<String> update(@RequestBody @Valid CodeGeneratorUpdateParam param) {
        CodeGeneratorInfo codeGeneratorInfo = new CodeGeneratorInfo();
        BeanUtils.copyProperties(param, codeGeneratorInfo);
        boolean result = generatorInfoService.updateById(codeGeneratorInfo);
        if(result){
            return success("修改配置成功");
        } else {
            return failure("修改配置失败");
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除配置")
    public Result<String> add(@PathVariable("id") String id) {
        boolean result = generatorInfoService.removeById(id);
        if(result){
            return success("删除配置成功");
        } else {
            return failure("删除配置失败");
        }
    }

}
