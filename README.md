# Mall-Coupons

<p align="center">
  <a href="#微信公众号"><img src="https://img.shields.io/badge/公众号-贝塔学JAVA-blue.svg" alt="公众号"></a>
  <a href="https://juejin.cn/user/2779199782521693"><img src="https://img.shields.io/badge/juejin-掘金-yellow.svg" alt="掘金"></a>
  <a href="https://blog.csdn.net/asdewq380303318"><img src="https://img.shields.io/badge/csdn-CSDN-red.svg" alt="CSDN"></a>
  <a href="https://my.oschina.net/u/3230120"><img src="https://img.shields.io/badge/oschina-开源中国-green" alt="开源中国"></a>
  <a href="https://www.zhihu.com/people/huaan9527-57/posts"><img src="https://img.shields.io/badge/zhihu-知乎-purple" alt="知乎"></a>
</p>

## 项目介绍
Mall-Coupons是一个从前端到后端完全开源的淘宝客项目，当初学习完uniapp之后想做一个实战项目，所以才研发了这个项目。由于本人平时主要从事后端研发，界面样式非我所长，所以大家觉得界面效果不好的可以自己修改。目前项目已经支持打包成App、微信小程序、QQ小程序、Web站点。

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

## 项目文档



## 效果预览
|  App下载地址   | QQ小程序  | 微信小程序  | Web站点 |
|  ----  | ----  | ----  | ----  |
| ![](https://raw.githubusercontent.com/silently9527/mall-coupons-server/master/doc/App%E4%B8%8B%E8%BD%BD%E5%9C%B0%E5%9D%80.png) | ![](https://raw.githubusercontent.com/silently9527/mall-coupons-server/master/doc/QQ%E5%B0%8F%E7%A8%8B%E5%BA%8F%E7%A0%81.png) | ![](https://raw.githubusercontent.com/silently9527/mall-coupons-server/master/doc/%E5%BE%AE%E4%BF%A1%E5%B0%8F%E7%A8%8B%E5%BA%8F%E7%A0%81.jpg) | ![](https://raw.githubusercontent.com/silently9527/mall-coupons-server/master/doc/web%E7%AB%99%E7%82%B9%E7%A0%81.png) |
| <img width="200" src="https://raw.githubusercontent.com/silently9527/mall-coupons-server/master/doc/App%E4%B8%8B%E8%BD%BD%E5%9C%B0%E5%9D%80.png"> | <img width="200" src="https://raw.githubusercontent.com/silently9527/mall-coupons-server/master/doc/QQ%E5%B0%8F%E7%A8%8B%E5%BA%8F%E7%A0%81.png"> | <img width="200" src="https://raw.githubusercontent.com/silently9527/mall-coupons-server/master/doc/%E5%BE%AE%E4%BF%A1%E5%B0%8F%E7%A8%8B%E5%BA%8F%E7%A0%81.jpg"> | <img width="200" src="https://raw.githubusercontent.com/silently9527/mall-coupons-server/master/doc/web%E7%AB%99%E7%82%B9%E7%A0%81.png"> |







## 组织结构

```
mall-coupons-server
├── doc -- 数据库脚本
├── lib -- 第三方依赖包
├── src -- 后端代码源码
├── static -- 静态页面，App下载静态页
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

## 架构图


## 部署步骤

Github地址：
Gitee地址：

前端项目地址：


## 微信公众号
<img width="200" src="https://raw.githubusercontent.com/silently9527/JavaCore/master/imgs/gonzhonghao.png" alt="公众号">


