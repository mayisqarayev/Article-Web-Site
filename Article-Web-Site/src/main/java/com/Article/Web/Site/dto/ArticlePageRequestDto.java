package com.Article.Web.Site.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticlePageRequestDto {
    private Integer pageNumber;
    private Integer pageSize;

    public ArticlePageRequestDto() {
    }

    public ArticlePageRequestDto(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
}
