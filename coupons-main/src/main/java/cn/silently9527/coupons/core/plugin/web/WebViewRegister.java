package cn.silently9527.coupons.core.plugin.web;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;

/**
 * web界面动态注册者
 * @author starBlues
 * @version 1.0
 */
public class WebViewRegister {

    private final WebMenuInfo webMenuInfo;

    private String appPath;
    private String appName;
    private String rootRouting;

    public WebViewRegister(WebMenuInfo webMenuInfo) {
        this.webMenuInfo = webMenuInfo;
    }

    public WebMenuInfo setView(String appPath, String rootRouting, String appName){
        Preconditions.checkArgument(!StrUtil.isEmpty(appPath), "appPath 不能为空");
        Preconditions.checkArgument(!StrUtil.isEmpty(rootRouting), "rootRouting 不能为空");
        this.appPath = appPath;
        this.appName = appName;
        this.rootRouting = rootRouting;
        return webMenuInfo;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppPath() {
        return appPath;
    }

    public String getRootRouting() {
        return rootRouting;
    }
}
