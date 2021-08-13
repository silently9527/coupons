package com.huaan9527.mall.webapi.service;

import com.huaan9527.mall.webapi.domain.AppreciateRelation;
import com.huaan9527.mall.webapi.domain.enums.DataType;
import com.huaan9527.mall.webapi.repository.AppreciateRelationRepository;
import com.huaan9527.mall.webapi.security.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AppreciateRelationService {
    private AppreciateRelationRepository appreciateRelationRepository;

    /**
     * 新增关注
     */
    @Transactional
    public void addAppreciate(DataType dataType, Long dataId) {
        AppreciateRelation appreciateRelation = buildAppreciateLog(dataType, dataId);
        appreciateRelationRepository.save(appreciateRelation);
    }

    @Transactional
    public void cancelAppreciate(DataType dataType, Long dataId) {
        Long userId = SecurityUtils.getCurrentUserId();
        appreciateRelationRepository.deleteByDataTypeAndDataId(userId, dataType, dataId);
    }

    private AppreciateRelation buildAppreciateLog(DataType dataType, Long dataId) {
        AppreciateRelation appreciateRelation = new AppreciateRelation();
        appreciateRelation.setDataId(dataId);
        appreciateRelation.setDataType(dataType.name());
        appreciateRelation.setUserId(SecurityUtils.getCurrentUserId());
        return appreciateRelation;
    }

    public List<AppreciateRelation> findAppreciateCollocations() {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == -1) {
            log.error("AppreciateRelationService.findAppreciateCollocations => 用户未登录");
            return new ArrayList<>();
        }
        return appreciateRelationRepository.findByUserIdAndDataType(userId, DataType.Collocation);
    }

    public AppreciateRelation findAppreciateCollocation(Long userId, Long collocationId, String dataType) {
        return appreciateRelationRepository.findByUserIdAndDataIdAndDataType(userId, collocationId, dataType);
    }
}
