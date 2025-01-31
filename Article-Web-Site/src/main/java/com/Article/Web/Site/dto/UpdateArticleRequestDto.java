package com.Article.Web.Site.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class UpdateArticleRequestDto {
    private String id;
    private String articleHeader;
    private String articleText;
    private LocalDate articleDeploymentDate;

    public UpdateArticleRequestDto() {
    }

    public UpdateArticleRequestDto(String id, String articleHeader, String articleText, LocalDate articleDeploymentDate) {
        this.id = id;
        this.articleHeader = articleHeader;
        this.articleText = articleText;
        this.articleDeploymentDate = articleDeploymentDate;
    }
}
