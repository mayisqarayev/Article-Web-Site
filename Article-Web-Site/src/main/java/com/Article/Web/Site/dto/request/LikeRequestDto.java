package com.Article.Web.Site.dto.request;


import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeRequestDto {

    private String fkLikerAccountId;
    private String fkLikedArticleId;

    public LikeRequestDto() {
    }

    public LikeRequestDto(String fkLikerAccountId, String fkLikedArticleId) {
        this.fkLikerAccountId = fkLikerAccountId;
        this.fkLikedArticleId = fkLikedArticleId;
    }
}
