package com.Article.Web.Site.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddImageRequestDto {

    String imageUrl;
    String fkArticleId;

    public AddImageRequestDto(String imageUrl, String fkArticleId) {
        this.imageUrl = imageUrl;
        this.fkArticleId = fkArticleId;
    }

    public AddImageRequestDto() {
    }
}
