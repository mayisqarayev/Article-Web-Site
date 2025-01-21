package com.Article.Web.Site.converter;

import com.Article.Web.Site.dto.AddCategoryRequestDto;
import com.Article.Web.Site.dto.CategoryResponseDto;
import com.Article.Web.Site.model.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public CategoryEntity toEntityFromAddCategoryRequestDto(AddCategoryRequestDto requestDto) {
        CategoryEntity entity = CategoryEntity.builder()
                .categoryName(requestDto.getCategoryName())
                .categoryStatus(true)
                .build();

        return entity;
    }

    public CategoryResponseDto toCategoryResponseDtoFromEntity(CategoryEntity entity) {
        return CategoryResponseDto.builder().categoryName(entity.getCategoryName()).build();
    }

}
