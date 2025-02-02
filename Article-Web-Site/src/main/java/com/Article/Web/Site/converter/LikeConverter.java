package com.Article.Web.Site.converter;

import com.Article.Web.Site.dto.request.LikeRequestDto;
import com.Article.Web.Site.model.LikeEntity;
import org.springframework.stereotype.Component;

@Component
public class LikeConverter {

    public LikeEntity toEntityFromLikeRequestDto(LikeRequestDto requestDto) {
        return LikeEntity.builder()
                .fkLikerAccountId(requestDto.getFkLikedArticleId())
                .fkLikedArticleId(requestDto.getFkLikedArticleId())
                .likeStatus(true)
                .build();
    }
}
