package com.Article.Web.Site.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ArticleResponseDto {
    private String articleHeader;
    private LocalDate articleDeploymentDate;
    private BigDecimal countOfReaders;
    private BigDecimal countOfLikes;
    private AccountResponseDto account;

    public ArticleResponseDto() {
    }

    public ArticleResponseDto(String articleHeader, LocalDate articleDeploymentDate, BigDecimal countOfReaders, BigDecimal countOfLikes,  AccountResponseDto account) {
        this.articleHeader = articleHeader;
        this.articleDeploymentDate = articleDeploymentDate;
        this.countOfReaders = countOfReaders;
        this.countOfLikes = countOfLikes;
        this.account = account;
    }
}
