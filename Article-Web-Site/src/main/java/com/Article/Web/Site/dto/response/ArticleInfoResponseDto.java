package com.Article.Web.Site.dto.response;

import com.Article.Web.Site.service.ScopedFunctions;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ArticleInfoResponseDto {
    private String articleHeader;
    private String articleText;
    private LocalDate articleDeploymentDate;
    private BigDecimal countOfReaders;
    private BigDecimal countOfLikes;
    private BigDecimal countOfComments;
    private CategoryResponseDto category;
    private AccountResponseDto account;
    private List<ImageResponseDto> images;

    public ArticleInfoResponseDto() {
    }

    public ArticleInfoResponseDto(String articleHeader, String articleText, LocalDate articleDeploymentDate, BigDecimal countOfReaders, BigDecimal countOfLikes, BigDecimal countOfComments, CategoryResponseDto category, AccountResponseDto account, List<ImageResponseDto> images) {
        this.articleHeader = articleHeader;
        this.articleText = articleText;
        this.articleDeploymentDate = articleDeploymentDate;
        this.countOfReaders = countOfReaders;
        this.countOfLikes = countOfLikes;
        this.countOfComments = countOfComments;
        this.category = category;
        this.account = account;
        this.images = images;
    }

    public ArticleInfoResponseDto apply(ScopedFunctions<ArticleInfoResponseDto> scopedFunctions) {
        return scopedFunctions.apply(this);
    }
}
