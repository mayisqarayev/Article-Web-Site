package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.CategoryConverter;
import com.Article.Web.Site.dto.request.AddCategoryRequestDto;
import com.Article.Web.Site.dto.request.CategoryRequestDto;
import com.Article.Web.Site.dto.response.CategoryResponseDto;
import com.Article.Web.Site.exception.CategoryNotFoundException;
import com.Article.Web.Site.exception.EmptyDataException;
import com.Article.Web.Site.exception.InvalidArgumentException;
import com.Article.Web.Site.model.CategoryEntity;
import com.Article.Web.Site.repo.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        Optional.ofNullable(requestDto).ifPresentOrElse(
                action -> {
                    repository.save(converter.toEntityFromAddCategoryRequestDto(requestDto));
                },
                () -> {
                    throw new InvalidArgumentException("Request is null");
                }
        );
    }

    public void deleteCategoryById(String id) {
        Optional.ofNullable(id).ifPresentOrElse(
                action -> {
                    repository.deleteCategoryById(id);
                },
                () -> {
                    throw new InvalidArgumentException("Id is null");
                }
        );
    }

    public CategoryResponseDto getCategoryById(String id) {
        Optional.ofNullable(id).orElseThrow(() -> new InvalidArgumentException("Id is null"));
        return converter.toCategoryResponseDtoFromEntity(repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"))//EH
        );
    }

    public List<CategoryResponseDto> getCategories() {
        List<CategoryEntity> categories = repository.findAll();
        if(categories.isEmpty()) throw new EmptyDataException("Categories is empty");

        return categories.stream()
                .map(converter::toCategoryResponseDtoFromEntity)
                .collect(Collectors.toList());
    }

    public List<CategoryResponseDto> getCategoriesByStatus(CategoryRequestDto requestDto) {
        Optional.ofNullable(requestDto).orElseThrow(() -> new InvalidArgumentException("Request is null"));
        List<CategoryEntity> categories = repository.findAll();
        if(categories.isEmpty()) throw new EmptyDataException("Categories is empty");

        return categories.stream()
                .filter(entity -> entity.getCategoryStatus().toString().equals(requestDto.getCategoryStatus().name()))
                .map(converter::toCategoryResponseDtoFromEntity)
                .collect(Collectors.toList());
    }

    public List<CategoryResponseDto> getLatestCategories() {
        List<CategoryEntity> categories = repository.findAll();
        if(categories.isEmpty()) throw new EmptyDataException("Categories is empty");
        Collections.reverse(categories);

        return categories.stream()
                .map(converter::toCategoryResponseDtoFromEntity)
                .collect(Collectors.toList());
    }
}