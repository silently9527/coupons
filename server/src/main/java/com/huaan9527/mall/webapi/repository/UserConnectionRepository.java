package com.huaan9527.mall.webapi.repository;

import com.huaan9527.mall.webapi.domain.UserConnection;
import org.springframework.data.mybatis.repository.MybatisRepository;

public interface UserConnectionRepository extends MybatisRepository<UserConnection, Long> {
    UserConnection findByUserId(String userId);
}
