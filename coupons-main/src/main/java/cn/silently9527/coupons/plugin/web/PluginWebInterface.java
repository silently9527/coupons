package cn.silently9527.coupons.plugin.web;

import cn.silently9527.coupons.core.plugin.web.WebViewRegister;


/**
 * 插件web界面定义接口
 * @author starBlues
 * @version 1.0
 */
public interface PluginWebInterface {

    /**
     * 配置界面
     * @param register 注册者
     */
    void config(WebViewRegister register);

}
