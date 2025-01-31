package com.Article.Web.Site.converter;

import com.Article.Web.Site.dto.request.AddImageRequestDto;
import com.Article.Web.Site.model.ImageEntity;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {

    public ImageEntity toImageEntityFromAddImageRequestDto(AddImageRequestDto requestDto) {
        return ImageEntity.builder()
                .imageUrl(requestDto.getImageUrl())
                .imageStatus(true)
                .fkArticleId(requestDto.getFkArticleId()).build();
    }

}
