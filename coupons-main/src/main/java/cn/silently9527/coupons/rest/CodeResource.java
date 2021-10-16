package cn.silently9527.coupons.rest;


import cn.hutool.core.io.FileUtil;
import cn.silently9527.coupons.rest.common.BaseResource;
import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import cn.silently9527.coupons.rest.model.CodeFile;
import cn.silently9527.coupons.service.CodeService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static cn.silently9527.coupons.rest.common.Result.failure;
import static cn.silently9527.coupons.rest.common.Result.success;

@Slf4j
@RestController
@RequestMapping(BaseResource.API + "code")
@Api(tags = "Code接口")
@AllArgsConstructor
public class CodeResource {
    private CodeService codeService;

    @GetMapping("/files")
    @ApiOperation("文件列表查询")
    public Result<List<CodeFile>> files() {
        List<CodeFile> files = codeService.files();
        return success(ApiEnum.GET_SUCCESS, files);
    }

    @GetMapping("/file/content")
    @ApiOperation("文件内容查询")
    public Result<String> fileContent(@RequestParam String path) {
        try {
            return success(ApiEnum.GET_SUCCESS, codeService.fileContent(path));
        } catch (Exception e) {
            return failure(ApiEnum.GET_ERROR, e);
        }
    }

    @PostMapping("/file/save")
    @ApiOperation("文件内容修改")
    public Result<String> saveFile(@RequestParam String path, String content) {
        try {
            codeService.saveFile(path, content);
            return success(ApiEnum.GET_SUCCESS, "", "保存成功");
        } catch (Exception e) {
            return failure(ApiEnum.GET_ERROR, "保存文件失败", e);
        }
    }


    @GetMapping("/download")
    @ApiOperation("代码下载")
    public ResponseEntity<byte[]> download() {
        try {
            String targetPath = codeService.packageAppCode();
            byte[] bytes = FileUtil.readBytes(new File(targetPath));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("下载出错:", e);
            return new ResponseEntity<>(JSON.toJSONString(failure(ApiEnum.GET_ERROR, "下载出错", e)).getBytes(StandardCharsets.UTF_8), HttpStatus.BAD_REQUEST);
        }
    }

}

