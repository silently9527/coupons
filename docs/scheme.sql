CREATE TABLE `UserConnection`
(
    `userId`         varchar(120) NOT NULL,
    `providerId`     varchar(120) NOT NULL,
    `providerUserId` varchar(120) NOT NULL,
    `rank`           int(11) NOT NULL,
    `displayName`    varchar(255) DEFAULT NULL,
    `profileUrl`     varchar(255) DEFAULT NULL,
    `imageUrl`       varchar(255) DEFAULT NULL,
    `accessToken`    varchar(255) NOT NULL,
    `secret`         varchar(255) DEFAULT NULL,
    `refreshToken`   varchar(255) DEFAULT NULL,
    `expireTime`     bigint(20) DEFAULT NULL,
    PRIMARY KEY (`userId`, `providerId`, `providerUserId`),
    UNIQUE KEY `UserConnectionRank` (`userId`, `providerId`, `rank`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `user`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `created_date`    datetime     DEFAULT CURRENT_TIMESTAMP,
    `updated_date`    datetime     DEFAULT NULL,
    `created_by`      bigint(20) DEFAULT NULL,
    `last_updated_by` bigint(20) DEFAULT NULL,
    `account`         varchar(60)  DEFAULT NULL,
    `password`        varchar(255) DEFAULT NULL,
    `user_type`       varchar(255) DEFAULT NULL,
    `nick`            varchar(50)  DEFAULT NULL,
    `mobile`          varchar(11)  DEFAULT NULL,
    `email`           varchar(20)  DEFAULT NULL,
    `status`          varchar(10)  DEFAULT NULL,
    `avatar_url`      varchar(500) DEFAULT NULL,
    `city`            varchar(30)  DEFAULT NULL,
    `country`         varchar(30)  DEFAULT NULL,
    `province`        varchar(30)  DEFAULT NULL,
    `sex`             tinyint(4) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY               `account_index` (`account`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `collection`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `created_date`    datetime    DEFAULT NULL,
    `updated_date`    datetime    DEFAULT NULL,
    `created_by`      bigint(20) DEFAULT NULL,
    `last_updated_by` bigint(20) DEFAULT NULL,

    `user_id`         bigint(20) DEFAULT NULL,
    `data_id`         bigint(20) DEFAULT NULL,
    `data_type`       varchar(20) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY               `user_id_index` (`user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `carousel`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `created_date`     datetime      DEFAULT NULL,
    `updated_date`     datetime      DEFAULT NULL,
    `created_by`       bigint(20) DEFAULT NULL,
    `last_updated_by`  bigint(20) DEFAULT NULL,

    `title`            varchar(50)   DEFAULT NULL,
    `image`            varchar(500)  DEFAULT NULL,
    `background_color` varchar(120)  DEFAULT NULL,
    `url`              varchar(1000) DEFAULT NULL,
    `status`           int(11) DEFAULT NULL,
    `top`              int(11) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `menu`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `created_date`    datetime      DEFAULT NULL,
    `updated_date`    datetime      DEFAULT NULL,
    `created_by`      bigint(20) DEFAULT NULL,
    `last_updated_by` bigint(20) DEFAULT NULL,

    `title`           varchar(50)   DEFAULT NULL,
    `label`           varchar(20)   DEFAULT NULL,
    `icon`            varchar(120)  DEFAULT NULL,
    `url_type`        varchar(50)   DEFAULT NULL,
    `url`             varchar(1000) DEFAULT NULL,
    `status`          int(11) DEFAULT NULL,
    `top`             int(11) DEFAULT NULL,
    `sort`            int(11) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `collocation`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `created_date`     datetime      DEFAULT NULL,
    `updated_date`     datetime      DEFAULT NULL,
    `created_by`       bigint(20) DEFAULT NULL,
    `last_updated_by`  bigint(20) DEFAULT NULL,

    `description`      varchar(1000) DEFAULT NULL,
    `main_image`       varchar(300)  DEFAULT NULL,
    `images`           varchar(2000) DEFAULT NULL,
    `view_count`       int(11) DEFAULT NULL,
    `appreciate_count` int(11) DEFAULT NULL,
    `source_type`      varchar(50)   DEFAULT NULL,
    `source_id`        varchar(100)  DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `collocation_product`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `created_date`    datetime    DEFAULT NULL,
    `updated_date`    datetime    DEFAULT NULL,
    `created_by`      bigint(20) DEFAULT NULL,
    `last_updated_by` bigint(20) DEFAULT NULL,

    `collocation_id`  bigint(20) DEFAULT NULL,
    `tb_goods_id`     varchar(50) DEFAULT NULL,
    `product_status`  varchar(20) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `tag`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `created_date`    datetime    DEFAULT NULL,
    `updated_date`    datetime    DEFAULT NULL,
    `created_by`      bigint(20) DEFAULT NULL,
    `last_updated_by` bigint(20) DEFAULT NULL,

    `name`            varchar(20) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `tag_relation`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `created_date`    datetime    DEFAULT NULL,
    `updated_date`    datetime    DEFAULT NULL,
    `created_by`      bigint(20) DEFAULT NULL,
    `last_updated_by` bigint(20) DEFAULT NULL,

    `tag_id`          bigint(20) DEFAULT NULL,
    `data_id`         bigint(20) DEFAULT NULL,
    `data_type`       varchar(40) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `appreciate_relation`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `created_date`    datetime    DEFAULT NULL,
    `updated_date`    datetime    DEFAULT NULL,
    `created_by`      bigint(20) DEFAULT NULL,
    `last_updated_by` bigint(20) DEFAULT NULL,

    `user_id`         bigint(20) DEFAULT NULL,
    `data_id`         bigint(20) DEFAULT NULL,
    `data_type`       varchar(40) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;


INSERT INTO `mall-coupons`.`menu` (`id`, `created_date`, `updated_date`, `created_by`, `last_updated_by`, `title`,
                                   `label`, `icon`, `url`, `status`, `top`, `sort`, `url_type`)
VALUES (1, '2020-07-05 21:46:20', '2020-07-05 21:46:24', 1, 1, '疯抢榜', '疯抢排行',
        'https://img.alicdn.com/imgextra/i2/2053469401/O1CN01IVi6Do2JJi1PQz6yq_!!2053469401.gif', '/pages/rank/rank', 1,
        10, 1, 'Native');
INSERT INTO `mall-coupons`.`menu` (`id`, `created_date`, `updated_date`, `created_by`, `last_updated_by`, `title`,
                                   `label`, `icon`, `url`, `status`, `top`, `sort`, `url_type`)
VALUES (2, '2020-07-05 21:48:35', '2020-07-05 21:48:44', 1, 1, '9.9包邮', '9.9包邮',
        'https://img.alicdn.com/imgextra/i4/2053469401/O1CN01yAF5em2JJi03dO5Rt_!!2053469401.png', '/pages/nine/nine', 1,
        10, 2, 'Native');
INSERT INTO `mall-coupons`.`menu` (`id`, `created_date`, `updated_date`, `created_by`, `last_updated_by`, `title`,
                                   `label`, `icon`, `url`, `status`, `top`, `sort`, `url_type`)
VALUES (12, '2020-09-07 22:50:37', '2020-09-07 22:50:41', 1, 1, '换季收纳', '换季收纳',
        'https://img.alicdn.com/imgextra/i1/2053469401/O1CN015iAm922JJhyGcttsX_!!2053469401.png',
        '/pages/product/list?subcid=8122', 1, 0, 4, 'Native');
INSERT INTO `mall-coupons`.`menu` (`id`, `created_date`, `updated_date`, `created_by`, `last_updated_by`, `title`,
                                   `label`, `icon`, `url`, `status`, `top`, `sort`, `url_type`)
VALUES (13, '2020-09-07 22:55:00', '2020-09-07 22:54:57', 1, 1, '牛仔裤', '牛仔裤',
        'https://img.alicdn.com/imgextra/i3/2053469401/O1CN01R94DUN2JJhyIjNoPh_!!2053469401.png',
        '/pages/product/list?subcid=111760', 1, 0, 5, 'Native');
INSERT INTO `mall-coupons`.`menu` (`id`, `created_date`, `updated_date`, `created_by`, `last_updated_by`, `title`,
                                   `label`, `icon`, `url`, `status`, `top`, `sort`, `url_type`)
VALUES (15, '2020-09-07 22:58:44', '2020-09-07 22:58:49', 1, 1, '旅行箱包', '旅行箱包',
        'https://img.alicdn.com/imgextra/i3/2053469401/TB29lWltuGSBuNjSspbXXciipXa-2053469401.png',
        '/pages/product/list?subcid=8304', 1, 0, 7, 'Native');
INSERT INTO `mall-coupons`.`menu` (`id`, `created_date`, `updated_date`, `created_by`, `last_updated_by`, `title`,
                                   `label`, `icon`, `url`, `status`, `top`, `sort`, `url_type`)
VALUES (16, '2020-09-07 23:00:12', '2020-09-07 23:00:08', 1, 1, '眼镜/墨镜', '眼镜/墨镜',
        'https://img.alicdn.com/imgextra/i2/2053469401/TB2iybFaHAaBuNjt_igXXb5ApXa-2053469401.png',
        '/pages/product/list?subcid=117972', 1, 0, 8, 'Native');
INSERT INTO `mall-coupons`.`menu` (`id`, `created_date`, `updated_date`, `created_by`, `last_updated_by`, `title`,
                                   `label`, `icon`, `url`, `status`, `top`, `sort`, `url_type`)
VALUES (17, '2020-10-10 21:50:23', '2020-10-10 21:50:28', 1, 1, '厨房专用', '厨房专用',
        'https://img.alicdn.com/imgextra/i1/2053469401/TB2tN1otuuSBuNjSsziXXbq8pXa-2053469401.png',
        '/pages/product/list?subcid=117967', 1, 0, 9, 'Native');
INSERT INTO `mall-coupons`.`menu` (`id`, `created_date`, `updated_date`, `created_by`, `last_updated_by`, `title`,
                                   `label`, `icon`, `url`, `status`, `top`, `sort`, `url_type`)
VALUES (18, '2020-10-10 21:52:58', '2020-10-10 21:53:01', 1, 1, '时尚毛呢', '时尚毛呢',
        'https://img.alicdn.com/imgextra/i2/2053469401/O1CN01AHW0za2JJhtdVzbXv-2053469401.png',
        '/pages/product/list?subcid=121334', 1, 0, 0, 'Native');