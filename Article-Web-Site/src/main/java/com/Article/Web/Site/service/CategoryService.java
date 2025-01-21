package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.CategoryConverter;
import com.Article.Web.Site.dto.AddCategoryRequestDto;
import com.Article.Web.Site.dto.CategoryResponseDto;
import com.Article.Web.Site.repo.CategoryRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryConverter converter;

    public CategoryService(CategoryRepository repository, CategoryConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public void addCategory(AddCategoryRequestDto requestDto) {
        repository.save(converter.toEntityFromAddCategoryRequestDto(requestDto));
    }

    public void deleteCategoryById(String id) {
        repository.deleteCategoryById(id);
    }

    public CategoryResponseDto getCategoryById(String id) {
        return converter.toCategoryResponseDtoFromEntity(repository.findById(id).get());
    }

}