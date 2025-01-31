package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.CategoryConverter;
import com.Article.Web.Site.dto.request.AddCategoryRequestDto;
import com.Article.Web.Site.dto.request.CategoryRequestDto;
import com.Article.Web.Site.dto.response.CategoryResponseDto;
import com.Article.Web.Site.model.CategoryEntity;
import com.Article.Web.Site.repo.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<CategoryResponseDto> getCategories() {
        return repository.findAll().stream()
                .map(converter::toCategoryResponseDtoFromEntity)
                .collect(Collectors.toList());
    }

    public List<CategoryResponseDto> getCategoriesByStatus(CategoryRequestDto requestDto) {
        return repository.findAll().stream()
                .filter(entity -> entity.getCategoryStatus().toString().equals(requestDto.getCategoryStatus().name()))
                .map(converter::toCategoryResponseDtoFromEntity)
                .collect(Collectors.toList());
    }

    public List<CategoryResponseDto> getLatestCategories() {
        List<CategoryEntity> categories = repository.findAll();
        Collections.reverse(categories);
        return categories.stream()
                .map(converter::toCategoryResponseDtoFromEntity)
                .collect(Collectors.toList());
    }
}