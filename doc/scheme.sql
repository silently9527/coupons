CREATE TABLE `UserConnection`
(
    `userId`         varchar(120) NOT NULL,
    `providerId`     varchar(120) NOT NULL,
    `providerUserId` varchar(120) NOT NULL,
    `rank`           int(11)      NOT NULL,
    `displayName`    varchar(255) DEFAULT NULL,
    `profileUrl`     varchar(255) DEFAULT NULL,
    `imageUrl`       varchar(255) DEFAULT NULL,
    `accessToken`    varchar(255) NOT NULL,
    `secret`         varchar(255) DEFAULT NULL,
    `refreshToken`   varchar(255) DEFAULT NULL,
    `expireTime`     bigint(20)   DEFAULT NULL,
    PRIMARY KEY (`userId`, `providerId`, `providerUserId`),
    UNIQUE KEY `UserConnectionRank` (`userId`, `providerId`, `rank`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `user`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `created_date`    datetime     DEFAULT CURRENT_TIMESTAMP,
    `updated_date`    datetime     DEFAULT NULL,
    `created_by`      bigint(20)   DEFAULT NULL,
    `last_updated_by` bigint(20)   DEFAULT NULL,
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
    `sex`             tinyint(4)   DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY `account_index` (`account`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `collection`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `created_date`    datetime    DEFAULT NULL,
    `updated_date`    datetime    DEFAULT NULL,
    `created_by`      bigint(20)  DEFAULT NULL,
    `last_updated_by` bigint(20)  DEFAULT NULL,

    `user_id`         bigint(20)  DEFAULT NULL,
    `data_id`         bigint(20)  DEFAULT NULL,
    `data_type`       varchar(20) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `carousel`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `created_date`     datetime      DEFAULT NULL,
    `updated_date`     datetime      DEFAULT NULL,
    `created_by`       bigint(20)    DEFAULT NULL,
    `last_updated_by`  bigint(20)    DEFAULT NULL,

    `title`            varchar(50)   DEFAULT NULL,
    `image`            varchar(500)  DEFAULT NULL,
    `background_color` varchar(120)  DEFAULT NULL,
    `url`              varchar(1000) DEFAULT NULL,
    `status`           int(11)       DEFAULT NULL,
    `top`              int(11)       DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `menu`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `created_date`    datetime      DEFAULT NULL,
    `updated_date`    datetime      DEFAULT NULL,
    `created_by`      bigint(20)    DEFAULT NULL,
    `last_updated_by` bigint(20)    DEFAULT NULL,

    `title`           varchar(50)   DEFAULT NULL,
    `label`           varchar(20)   DEFAULT NULL,
    `icon`            varchar(120)  DEFAULT NULL,
    `url`             varchar(1000) DEFAULT NULL,
    `status`          int(11)       DEFAULT NULL,
    `top`             int(11)       DEFAULT NULL,
    `sort`            int(11)       DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `collocation`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `created_date`     datetime      DEFAULT NULL,
    `updated_date`     datetime      DEFAULT NULL,
    `created_by`       bigint(20)    DEFAULT NULL,
    `last_updated_by`  bigint(20)    DEFAULT NULL,

    `description`      varchar(1000) DEFAULT NULL,
    `main_image`       varchar(300)  DEFAULT NULL,
    `images`           varchar(2000) DEFAULT NULL,
    `view_count`       int(11)       DEFAULT NULL,
    `appreciate_count` int(11)       DEFAULT NULL,
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
    `created_by`      bigint(20)  DEFAULT NULL,
    `last_updated_by` bigint(20)  DEFAULT NULL,

    `collocation_id`  bigint(20)  DEFAULT NULL,
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
    `created_by`      bigint(20)  DEFAULT NULL,
    `last_updated_by` bigint(20)  DEFAULT NULL,

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
    `created_by`      bigint(20)  DEFAULT NULL,
    `last_updated_by` bigint(20)  DEFAULT NULL,

    `tag_id`          bigint(20)  DEFAULT NULL,
    `data_id`         bigint(20)  DEFAULT NULL,
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
    `created_by`      bigint(20)  DEFAULT NULL,
    `last_updated_by` bigint(20)  DEFAULT NULL,

    `user_id`         bigint(20)  DEFAULT NULL,
    `data_id`         bigint(20)  DEFAULT NULL,
    `data_type`       varchar(40) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;

