package cn.silently9527.coupons.rest;

import cn.silently9527.coupons.config.prop.WebProp;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author starBlues
 * @version 1.0
 */
@RestController
@RequestMapping("/config.js")
public class WebResource {

    private final String configJs;

    public WebResource(WebProp webProp, Gson gson) {
        Map<String, Object> configJsConfig = webProp.getConfig();
        configJsConfig.put("env", "prod");
        this.configJs = "window.config = " + gson.toJson(configJsConfig);
    }


    @GetMapping(produces = "application/javascript;charset=UTF-8")
    public String getConfigJs(){
        return configJs;
    }

}
