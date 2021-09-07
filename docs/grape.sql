/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50648
 Source Host           : 127.0.0.1:3306
 Source Schema         : grape

 Target Server Type    : MySQL
 Target Server Version : 50648
 File Encoding         : 65001

 Date: 05/02/2021 16:20:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for code_generator_info
-- ----------------------------
DROP TABLE IF EXISTS `code_generator_info`;
CREATE TABLE `code_generator_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `driver_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '驱动名称',
  `url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'url地址',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `base_package_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '生成的基本包名',
  `table_names` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '生成的表名(全部生成则为*, 多个用逗号分隔)',
  `gmt_created` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  `author` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成者信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of code_generator_info
-- ----------------------------
INSERT INTO `code_generator_info` VALUES ('fa86f96a28d170dfdb71af7896a338ed', 'blocks', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://127.0.0.1:3306/blocks?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC', 'root', 'root', 'com.gitee.starblues.grape.repository.databases', '*', '2021-01-13 15:41:58', 'starBlues', NULL);

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict`  (
  `dict_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据字典id',
  `dict_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典的唯一key',
  `label` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典标签',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典值',
  `order_num` int(11) NOT NULL COMMENT '排序(数字越小越靠前)',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建的用户',
  `gmt_created` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  `modified_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改的用户',
  `gmt_modified` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `username` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `gmt_login_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录时间',
  `gmt_login_timestamp` bigint(20) NOT NULL COMMENT '登录的时间戳',
  `login_ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录ip',
  `login_result` int(1) NOT NULL COMMENT '登录结果(1成功, 2失败)',
  `login_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录地址',
  `login_failure_msg` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录失败原因',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '登录日志' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `menu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单id',
  `parent_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '父菜单id,如果是顶级菜单, 则为0',
  `type` int(1) NOT NULL COMMENT '类型(1: 目录、2: 菜单、3: 权限)',
  `title` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `permissions` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限标识, 多个可用逗号分隔',
  `component` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'vue组件名称, 只有类型为1、2才有值',
  `order_num` int(11) NOT NULL DEFAULT 1 COMMENT '排序，数字越小越靠前',
  `icon` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `path` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路径地址(也是路由地址)',
  `enable` int(1) NOT NULL DEFAULT 1 COMMENT '是否启用(1启用,0禁用)',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否被删除(1被删除, 0未被删除)',
  `html_target` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'html target 属性(_blank, _self, _parent, _top)',
  `plugin_menu` int(1) NOT NULL DEFAULT 0 COMMENT '是否为插件菜单(1是，0不是)',
  `plugin_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '插件id',
  `plugin_app_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '插件界面app名称',
  `plugin_app_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '插件界面路径',
  `plugin_root_routing` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '插件根路由',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建的用户',
  `gmt_created` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  `modified_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改的用户',
  `gmt_modified` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单模型表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('0db4da0a636bbf7d94043b58859d0787', '104', 3, '删除客户端', 'oauthClient:delete', NULL, 1044, NULL, NULL, 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-02-05 15:41:21', 'admin', '2021-02-05 15:42:55');
INSERT INTO `menu` VALUES ('100', '0', 1, '系统管理', 'system', 'RouteView', 100, 'tool', '', 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-01-01 08:08:08', 'admin', '2021-01-01 08:08:08');
INSERT INTO `menu` VALUES ('101', '100', 2, '菜单管理', 'menu-mange', 'MenuManage', 101, 'menu', '/system/menu-mange', 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-01-01 08:08:08', 'admin', '2021-01-28 17:10:25');
INSERT INTO `menu` VALUES ('102', '100', 2, '角色管理', 'role:query', 'RoleManage', 102, 'team', '/system/role-manage', 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-01-01 08:08:08', 'admin', '2021-02-05 15:46:21');
INSERT INTO `menu` VALUES ('103', '100', 2, '用户管理', 'user:query', 'UserManage', 103, 'user', '/system/user-mange', 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-01-01 08:08:08', 'admin', '2021-02-05 15:46:05');
INSERT INTO `menu` VALUES ('104', '100', 2, '客户端授权', 'oauthClient:query', 'OAuthClientManage', 104, 'key', '/system/oauth-client-manage', 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-01-01 08:08:08', 'admin', '2021-02-05 15:45:51');
INSERT INTO `menu` VALUES ('105', '100', 2, '登录日志', 'loginLog:query', 'LoginLogManage', 105, 'container', '/system/login-log-manage', 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-01-01 08:08:08', 'admin', '2021-01-01 08:08:08');
INSERT INTO `menu` VALUES ('16697d9f8e2e80dc6259735f77a21c57', '102', 3, '删除角色', 'role:delete', NULL, 1025, NULL, NULL, 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-02-05 15:31:36', 'admin', '2021-02-05 15:33:42');
INSERT INTO `menu` VALUES ('200', '0', 1, '插件模块', 'plugin', 'RouteView', 200, 'api', NULL, 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-01-01 08:08:08', 'admin', '2021-01-28 17:10:56');
INSERT INTO `menu` VALUES ('201', '200', 2, '插件管理', 'plugin:query', 'PluginManage', 201, 'pushpin', '/plugin/plugin-manage', 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-01-01 08:08:08', 'admin', '2021-02-05 15:49:16');
INSERT INTO `menu` VALUES ('284ea6444b2019b35b04cf0431e71f4f', '101', 3, '修改菜单', 'menu:update', NULL, 1012, NULL, NULL, 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-02-05 11:39:14', 'admin', '2021-02-05 15:34:16');
INSERT INTO `menu` VALUES ('3abb3df2fb6a6e0e935ce0c48d60cc18', '103', 3, '重置密码', 'user:resetPassword', NULL, 1034, NULL, NULL, 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-02-05 15:39:06', 'admin', '2021-02-05 15:39:13');
INSERT INTO `menu` VALUES ('4de5ae6164d2775234afcaae8c0200ba', '101', 3, '新增菜单', 'menu:add', NULL, 1011, NULL, NULL, 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-02-05 11:26:21', 'admin', '2021-02-05 15:34:07');
INSERT INTO `menu` VALUES ('53b2efe2a03f235206f6779ba4dec994', '103', 3, '修改用户', 'user:update', NULL, 1033, NULL, NULL, 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-02-05 15:37:40', 'admin', '2021-02-05 15:38:10');
INSERT INTO `menu` VALUES ('69d149990d7463fb03ec04751d88f73c', '102', 3, '添加角色', 'role:add', NULL, 1022, NULL, NULL, 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-02-05 15:30:26', 'admin', '2021-02-05 15:33:19');
INSERT INTO `menu` VALUES ('7271a7fe4e3de307c8ada965f2bff9b6', '102', 3, '分配权限', 'role:setMenu', NULL, 1024, NULL, NULL, 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-02-05 15:30:00', 'admin', '2021-02-05 15:33:35');
INSERT INTO `menu` VALUES ('798208edefa950debc13a08060a0f865', '103', 3, '删除用户', 'user:delete', NULL, 1035, NULL, NULL, 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-02-05 15:39:37', 'admin', '2021-02-05 15:39:37');
INSERT INTO `menu` VALUES ('bf511230be6e8054c02c2551cef19a03', '101', 3, '删除菜单', 'menu:delete', NULL, 1013, NULL, NULL, 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-02-05 14:19:52', 'admin', '2021-02-05 15:34:22');
INSERT INTO `menu` VALUES ('c475623eac48db229e5ed6622bd9deea', '104', 3, '修改客户端', 'oauthClient:update', NULL, 1043, NULL, NULL, 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-02-05 15:40:58', 'admin', '2021-02-05 15:43:27');
INSERT INTO `menu` VALUES ('cd91a7ea6ce0e6eaab3696f12cf7b1d4', '103', 3, '新增用户', 'user:add', NULL, 1032, NULL, NULL, 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-02-05 15:38:02', 'admin', '2021-02-05 15:38:17');
INSERT INTO `menu` VALUES ('code-generator', '200', 2, '代码生成', 'code-generator', NULL, 2147483647, 'code', '/code-generator', 1, 0, '', 1, 'system-tools', 'system-tools-console', '/system-tools/index.html', '/system-tools', 'NOT_FOUND', '2021-01-31 14:06:51', 'NOT_FOUND', '2021-02-05 15:56:37');
INSERT INTO `menu` VALUES ('d0fa2c55ffe1d336ff6b84fd0fffc07f', '104', 3, '新增客户端', 'oauthClient:add', NULL, 1042, NULL, NULL, 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-02-05 15:43:43', 'admin', '2021-02-05 15:43:43');
INSERT INTO `menu` VALUES ('d37c4cda3010f1bf20ae28945f1f3404', '102', 3, '修改角色', 'role:update', NULL, 1023, NULL, NULL, 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-02-05 15:30:47', 'admin', '2021-02-05 15:33:26');
INSERT INTO `menu` VALUES ('fe9075b88d8925d94d263814f78947a9', '201', 3, '操作插件', 'plugin:operate', NULL, 2011, NULL, NULL, 1, 0, '', 0, NULL, NULL, NULL, NULL, 'admin', '2021-02-05 15:49:54', 'admin', '2021-02-05 15:50:01');

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端ID',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `resource_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源ID集合,多个资源时用逗号(,)分隔',
  `client_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端密匙',
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端申请的权限范围',
  `authorized_grant_types` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端支持的grant_type',
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '重定向URI',
  `authorities` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔',
  `access_token_validity` int(11) NULL DEFAULT NULL COMMENT '访问令牌有效时间值(单位:秒)',
  `refresh_token_validity` int(11) NULL DEFAULT NULL COMMENT '更新令牌有效时间值(单位:秒)',
  `additional_information` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段',
  `autoapprove` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户是否自动Approval操作',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建用户',
  `gmt_created` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  `modified_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改用户',
  `gmt_modified` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改时间',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '授权客户端表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('admin_client', '默认', NULL, '$2a$10$3EDXLXfW1O8ZlJHnqdfTeOG6qEzcGJkIY1tF3d3xnakeztqGqnX.q', 'all', 'authorization_code,refresh_token,password', NULL, NULL, 36000, 36000, NULL, NULL, 'admin', '2021-01-01 08:08:08', 'admin', '2021-01-01 08:08:08', NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  `name` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '帐号状态（1启用, 0停用）',
  `deleted` int(1) NOT NULL COMMENT '删除标记（1删除, 0 正常）',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建用户',
  `gmt_created` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  `modified_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改用户',
  `gmt_modified` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改时间',
  `description` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin', 'super_admin', 1, 0, 'admin', '2021-01-01 08:08:08', 'admin', '2021-01-08 15:01:08', '超级管理员');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  `menu_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单id',
  `is_parent` int(1) NOT NULL COMMENT '是否父节点（0否 1是）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '1', '1', 0);
INSERT INTO `role_menu` VALUES ('7136b7316faf4d0faa4cb03583754862', '917d7b3f19da4b0bfd31d747fb5971ad', '102', 0);
INSERT INTO `role_menu` VALUES ('873aefbb44de40819ec3289484e1e0db', '917d7b3f19da4b0bfd31d747fb5971ad', '100', 0);
INSERT INTO `role_menu` VALUES ('ba5128fae2b74be1871d8a1002be49c9', '917d7b3f19da4b0bfd31d747fb5971ad', '101', 0);
INSERT INTO `role_menu` VALUES ('ba77c34d9d144deb9bf146c71b309ac5', '917d7b3f19da4b0bfd31d747fb5971ad', '103', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码(加密后)',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '帐号状态（1启用, 0停用）',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '删除标记（1删除, 0 正常）暂未使用',
  `locked` int(6) NOT NULL DEFAULT 0 COMMENT '是否被锁(小于等于5表示未被锁, 大于5表示被锁)',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建用户',
  `gmt_created` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  `modified_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改用户',
  `gmt_modified` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改时间',
  `avatar` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `last_login_ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上一次登录的ip地址',
  `last_gmt_login_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上一次登录的时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '16666666666', '123@qq.com', 'admin', '$2a$10$grIYlsR1p2VvKambuuwSLO/R/3q.pauwrTiuxw36yyqvxTynjLPTG', 1, 0, 0, 'admin', '2021-01-01 08:08:08', 'admin', 'admin', '2e418339355940a9b83f72d597a6da2c.jpg', '127.0.0.1', '2021-02-05 16:17:16');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户角色关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');

SET FOREIGN_KEY_CHECKS = 1;
