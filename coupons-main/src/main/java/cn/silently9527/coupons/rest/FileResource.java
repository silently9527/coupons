package cn.silently9527.coupons.rest;

import cn.silently9527.coupons.core.security.CurrentUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件访问接口
 * @author starBlues
 * @version 1.0
 */
@Controller
@RequestMapping("/file")
@Api(tags = "文件访问接口")
@AllArgsConstructor
public class FileResource {

    private final CurrentUserService userService;

    @GetMapping(value = "/avatar/{fileName}",produces = MediaType.IMAGE_JPEG_VALUE)
    @ApiOperation("访问头像的接口")
    @ResponseBody
    public BufferedImage getImage(@PathVariable("fileName") String fileName) throws IOException {
        InputStream inputStream = userService.getAvatar(fileName);
        try {
            return ImageIO.read(inputStream);
        } finally {
            inputStream.close();
        }
    }

}
