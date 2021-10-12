DROP TABLE IF EXISTS `plugin`;
CREATE TABLE `plugin`
(
    `id`            varchar(32)  NOT NULL COMMENT 'id',
    `create_user`   varchar(32)  NOT NULL COMMENT '创建用户',
    `gmt_created`   varchar(32)  NOT NULL COMMENT '创建时间',
    `modified_user` varchar(32)  NOT NULL COMMENT '修改用户',
    `gmt_modified`  varchar(32)  NOT NULL COMMENT '修改时间',
    `plugin_code`   varchar(32)  NOT NULL COMMENT '插件code',
    `plugin_name`   varchar(32)  NOT NULL COMMENT '插件名称',
    `icon`          varchar(255) NOT NULL COMMENT '图标',
    `author`        varchar(30)  NOT NULL COMMENT '作者',
    `version`       varchar(10)  NOT NULL COMMENT '版本',
    `description`   varchar(255) NOT NULL COMMENT '描述',
    `download_url`  varchar(255) NOT NULL COMMENT '下载地址',
    `doc_url`       varchar(255) NOT NULL COMMENT '文档地址',
    `price`         varchar(10)  NOT NULL COMMENT '价格',
    `status`        tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态（1启用, 0停用）',
    `qrcode`        varchar(255) NOT NULL COMMENT '二维码',
    `remark`        varchar(255) NOT NULL COMMENT '引导',
    `password`      varchar(30)  NOT NULL COMMENT '提取码',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '客户端的tabBar配置';
