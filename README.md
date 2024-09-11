
<p align="center">
    <a href="https://t.zsxq.com/h2EIR" target="_blank"><img alt="" src="https://img.shields.io/badge/知识星球-Herman's Notes-red&logoColor=FC5531" /></a>
  <a href="#微信公众号"><img src="https://img.shields.io/badge/公众号-贝塔学JAVA-blue.svg" alt="公众号"></a>
  <a href="https://juejin.cn/user/2779199782521693"><img src="https://img.shields.io/badge/juejin-掘金-yellow.svg" alt="掘金"></a>
  <a href="https://blog.csdn.net/asdewq380303318"><img src="https://img.shields.io/badge/csdn-CSDN-red.svg" alt="CSDN"></a>
  <a href="https://my.oschina.net/u/3230120"><img src="https://img.shields.io/badge/oschina-开源中国-green" alt="开源中国"></a>
  <a href="https://www.zhihu.com/people/huaan9527-57/posts"><img src="https://img.shields.io/badge/zhihu-知乎-purple" alt="知乎"></a>
</p>


# coupons文档完善中...

## 项目介绍
coupons是一个从前端到后端完全开源的淘宝客项目，目前项目已经支持打包成App、微信小程序、QQ小程序、Web站点；理论上其他小程序支持，可能需要微调

#### 欢迎加微信`silently9527`,加入技术交流群

### Github地址：
- 项目地址：[https://github.com/silently9527/coupons](https://github.com/silently9527/coupons)
### Gitee地址：
- 项目地址：[https://gitee.com/silently9527/coupons](https://gitee.com/silently9527/coupons)

## 功能列表
- [x] 推荐穿衣搭配
- [x] 搭配筛选
- [x] 搭配详情
- [x] 相关搭配推荐
- [x] 用户点赞
- [x] 商品分类
- [x] 分类查询商品列表
- [x] 首页轮播
- [x] APP、Web支持唤醒淘宝
- [x] 9.9包邮
- [x] 疯抢排行榜
- [x] 首页优质商品推荐
- [x] 商品、优惠券搜索
- [x] 商品详情
- [x] 相似商品推荐
- [x] 商品收藏、收藏夹
- [x] 口令购买、领券购买
- [x] 用户登录、微信登录、QQ登录、手机验证码登录
- [x] 用户新手教程

## 在线体验地址
<table>
    <tbody>
        <tr>
            <td align="center">App下载地址</td>
            <td align="center">QQ小程序</td>
            <td align="center">微信小程序</td>
            <td align="center">Web站点</td>
        </tr>
        <tr style="background-color: white;">
            <td align="center"><img width="200" src="https://tva1.sinaimg.cn/large/008eGmZEgy1gn4esj3uutj30b40b4jsx.jpg"></td>
            <td align="center"><img width="200" src="https://tva1.sinaimg.cn/large/008eGmZEgy1gn4et70ft2j30g40g4gm6.jpg"></td>
            <td align="center"><img width="200" src="https://tva1.sinaimg.cn/large/008eGmZEgy1gn4etut7d1j3076076aa2.jpg"></td>
            <td align="center"><img width="200" src="https://tva1.sinaimg.cn/large/008eGmZEgy1gn4euhcqonj30b40b43yt.jpg"></td>
        </tr>
    </tbody>
</table>

App下载地址(用手机访问才能正确下载IOS和安卓版本): [http://static.szjx.top/download/index.html](http://static.szjx.top/download/index.html)

Web站点(用手机访问，PC端未适配)：[http://m.szjx.top](http://m.szjx.top)

## 效果预览
<table>
    <tbody>
        <tr style="background-color: white;">
            <td align="center"><img width="200" src="https://tva1.sinaimg.cn/large/008eGmZEly1gn4hfiqyqoj30ku11240m.jpg"></td>
            <td align="center"><img width="200" src="https://tva1.sinaimg.cn/large/008eGmZEly1gn4hggo8thj30ku112aba.jpg"></td>
            <td align="center"><img width="200" src="https://tva1.sinaimg.cn/large/008eGmZEly1gn4hge5bwuj30ku112my9.jpg"></td>
            <td align="center"><img width="200" src="https://tva1.sinaimg.cn/large/008eGmZEly1gn4hgbc1e2j30ku112dhz.jpg"></td>
        </tr>
        <tr style="background-color: white;">
            <td align="center"><img width="200" src="https://tva1.sinaimg.cn/large/008eGmZEly1gn4hg8p7uhj30ku112acg.jpg"></td>
            <td align="center"><img width="200" src="https://tva1.sinaimg.cn/large/008eGmZEly1gn4hg5kj8lj30ku112tc6.jpg"></td>
            <td align="center"><img width="200" src="https://tva1.sinaimg.cn/large/008eGmZEly1gn4hg10sibj30ku112acs.jpg"></td>
            <td align="center"><img width="200" src="https://tva1.sinaimg.cn/large/008eGmZEly1gn4hft8rzcj30ku1123yt.jpg"></td>
        </tr>
    </tbody>
</table>


## 组织结构

```
coupons
├── doc -- 数据库脚本
├── server -- 后端源码
├── client -- 前端源码
```

## 技术选型

#### 后端技术
|  技术   | 备注  | 地址  |
|  ----  | ----  |----  |
| SpringBoot  | 容器+MVC框架 | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
| MyBatis  | 	ORM框架 | [http://www.mybatis.org/mybatis-3/zh/index.html](http://www.mybatis.org/mybatis-3/zh/index.html) |
| SpringSecurity  | 认证和授权框架 | [https://spring.io/projects/spring-security](https://spring.io/projects/spring-security) |
| SpringSocial  | OAuth2认证框架 | [https://github.com/spring-projects/spring-social](https://github.com/spring-projects/spring-social) |
| Redis  | 分布式缓存 | [https://redis.io/](https://redis.io/) |
| Druid  | 数据库连接池 | [https://github.com/alibaba/druid](https://github.com/alibaba/druid) |
| Lombok  | 简化对象封装工具 | [https://github.com/rzwitserloot/lombok](https://github.com/rzwitserloot/lombok) |
| Fastjson  | JSON工具 | https://github.com/alibaba/fastjson |
| spring-data-mybatis  | 封装Mybatis实现JPA部分功能 | [https://github.com/easybest/spring-data-mybatis](https://github.com/easybest/spring-data-mybatis) |

#### 前端技术
|  技术   | 备注  | 地址  |
|  ----  | ----  |----  |
| Vue  | 前端框架 | [https://vuejs.org/](https://vuejs.org/) |
| UniApp | 一个使用 Vue.js 开发所有前端应用的框架 | [https://uniapp.dcloud.io/](https://uniapp.dcloud.io/) |
| Vuex | 全局状态管理框架 | [https://vuex.vuejs.org/](https://vuex.vuejs.org/) |
| colorui | 样式库 | [https://github.com/weilanwl/ColorUI](https://github.com/weilanwl/ColorUI) |

### 开发环境
| 工具          | 版本号 | 下载                                                         |
| ------------- | ------ | ------------------------------------------------------------ |
| JDK           | 1.8    | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html |
| Mysql         | 5.7    | https://www.mysql.com/                                       |
| Redis         | 5.0    | https://redis.io/download                                    |
| Nginx         | 1.10   | http://nginx.org/en/download.html                            |

## 部署文档
* [淘客项目coupons在 Linux 环境部署指南](https://silently9527.cn/?p=67)
* [使用Docker镜像部署Coupons淘宝客项目](https://silently9527.cn/?p=70)

**有任何部署疑问，欢迎给我留言**

#### 期待你的加入，公众号：`贝塔学Java`，个人微信号：silently9527

<img src="https://cdn.silently9527.cn/weixhao_gongzonghao_1629032267170.jpg?imageView2/1/w/600/h/350" alt="公众号">

## 其他项目推荐
* [Idea工具箱插件](https://github.com/silently9527/Toolkit)
* [深入解析SpringMVC核心原理：从手写简易版MVC框架开始(SmartMvc)](https://github.com/silently9527/SmartMvc)
* [Java程序员自我学习的书单](https://github.com/silently9527/ProgrammerBooks)
* [技术文章以及代码收录仓库](https://github.com/silently9527/ProgrammerNotes)
* [高颜值可定制化的简介导航网站](http://nav.silently9527.cn/)


### 博客地址
[https://herman7z.site](https://herman7z.site)

### 知识星球: [Herman's Notes](https://t.zsxq.com/h2EIR)
![](https://raw.githubusercontent.com/silently9527/images/main/202408151725390.png)
