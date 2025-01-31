package com.Article.Web.Site.dto.request;

import com.Article.Web.Site.model.enums.CategoryStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequestDto {
    CategoryStatus categoryStatus;
    public CategoryRequestDto(CategoryStatus categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public CategoryRequestDto() {
    }
}