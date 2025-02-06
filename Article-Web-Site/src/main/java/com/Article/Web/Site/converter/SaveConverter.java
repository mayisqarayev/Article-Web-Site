package com.Article.Web.Site.converter;

import com.Article.Web.Site.dto.request.SaveRequestDto;
import com.Article.Web.Site.model.SaveEntity;
import org.springframework.stereotype.Component;

@Component
public class SaveConverter {


    public SaveEntity toEntityFromSaveRequestDto(SaveRequestDto requestDto) {
        return SaveEntity.builder()
                .saveStatus(true)
                .fkSaverAccountId(requestDto.getFkSaverAccountId())
                .fkSavedArticleId(requestDto.getFkSavedArticleId()).build();
    }
}
