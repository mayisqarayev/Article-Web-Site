package com.Article.Web.Site.converter;

import com.Article.Web.Site.dto.request.AddTagRequestDto;
import com.Article.Web.Site.model.TagEntity;
import org.springframework.stereotype.Component;

@Component
public class TagConverter {


    public TagEntity toEntityFromAddTagRequestDto(AddTagRequestDto requestDto) {
        return TagEntity.builder()
                .tagName(requestDto.getTagName())
                .tagStatus(true)
                .fkArticleId(requestDto.getArticleId()).build();
    }
}
