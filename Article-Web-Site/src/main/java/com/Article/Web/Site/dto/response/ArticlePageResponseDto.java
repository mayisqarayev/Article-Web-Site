package com.Article.Web.Site.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ArticlePageResponseDto {
    private Integer totalPages;
    private Long totalElements;
    private Boolean hasNext;
    private List<ArticleResponseDto> content;

    public ArticlePageResponseDto() {
    }

    public ArticlePageResponseDto(Integer totalPages, Long totalElements, Boolean hasNext, List<ArticleResponseDto> content) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.hasNext = hasNext;
        this.content = content;
    }
}
