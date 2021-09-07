package cn.silently9527.coupons.core.plugin.web;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

/**
 * @author starBlues
 * @version 1.0
 */
public class WebMenuInfo {

    private final Map<String, MenuDefine> menuDefineMap = Maps.newHashMap();

    public WebMenuInfo() {
    }


    public WebMenuInfo addMenu(String id, MenuDefine define) {
        Preconditions.checkArgument(!StrUtil.isEmpty(id), "id 不能为空");
        Preconditions.checkNotNull(define, "MenuDefine 不能为空");
        if(id.length() >= 100){
            throw new RuntimeException("id太长(最多100个字符)：" + id);
        }
        menuDefineMap.put(id, define);
        return this;
    }

    public Map<String, MenuDefine> getMenuDefineMap(){
        return Collections.unmodifiableMap(menuDefineMap);
    }


}
