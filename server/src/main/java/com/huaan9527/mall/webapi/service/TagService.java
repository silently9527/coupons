package com.huaan9527.mall.webapi.service;

import com.huaan9527.mall.webapi.domain.Tag;
import com.huaan9527.mall.webapi.domain.TagRelation;
import com.huaan9527.mall.webapi.repository.TagRelationRepository;
import com.huaan9527.mall.webapi.repository.TagRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TagService {
    private TagRepository tagRepository;
    private TagRelationRepository tagRelationRepository;

    @Transactional
    public void addTag(String dataType, Long dataId, String tagName) {
        Tag tag = tagRepository.findByName(tagName);
        if (Objects.isNull(tag)) {
            tag = new Tag();
            tag.setName(tagName);
        }
        tagRepository.save(tag);

        TagRelation tagRelation = new TagRelation();
        tagRelation.setDataId(dataId);
        tagRelation.setDataType(dataType);
        tagRelation.setTagId(tag.getId());
        tagRelationRepository.save(tagRelation);
    }

}
