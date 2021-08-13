package com.huaan9527.mall.webapi.repository;

import com.huaan9527.mall.webapi.domain.User;
import org.springframework.data.mybatis.repository.MybatisRepository;

public interface UserRepository extends MybatisRepository<User, Long> {
    User findByMobile(String account);

    long countByMobile(String mobile);

    long countByEmail(String mobile);
}
