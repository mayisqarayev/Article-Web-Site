package com.Article.Web.Site.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponseDto {

    private String categoryName;

    public CategoryResponseDto() {
    }

    public CategoryResponseDto(String categoryName) {
        this.categoryName = categoryName;
    }
}
