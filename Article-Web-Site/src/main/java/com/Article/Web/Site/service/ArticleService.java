package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.ArticleConverter;
import com.Article.Web.Site.dto.*;
import com.Article.Web.Site.model.ArticleEntity;
import com.Article.Web.Site.repo.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository repository;
    private final ArticleConverter converter;
    private final ImageService imageService;

    public ArticleService(ArticleRepository repository, ArticleConverter converter, ImageService imageService) {
        this.repository = repository;
        this.converter = converter;
        this.imageService = imageService;
    }

    public void addArticle(AddArticleRequestDto requestDto) {
        ArticleEntity savedEntity = repository.save(converter.toArticleEntityFromAddArticleRequestDto(requestDto));
        requestDto.getAddedImages().forEach(imageService::addImage);
        repository.updateArticleEntityImagesById(
                savedEntity.getId(),
                requestDto.getAddedImages().stream()
                        .map(imageService::transformImageEntity)
                        .collect(Collectors.toList())
        );
    }

    public void deleteArticleById(String id) {
        repository.deleteArticleEntityById(id);
    }

    public ArticleInfoResponseDto getArticleById(String id) {
        return converter.toArticleInfoResponseDtoFromEntity(repository.findById(id).get());
    }

    public List<ArticleResponseDto> getAllArticles() {
        return repository.findAll().stream()
                .map(converter::toArticleResponseDtoFromEntity)
                .sorted(Comparator.comparing(ArticleResponseDto::getArticleDeploymentDate).reversed())
                .collect(Collectors.toList());
    }

    public ArticlePageResponseDto getArticlesPagination(ArticlePageRequestDto requestDto) {
        Page<ArticleEntity> entityPage = repository.findAll(PageRequest.of(
                requestDto.getPageNumber(), requestDto.getPageSize()
        ));
        return ArticlePageResponseDto.builder()
                .totalElements(entityPage.getTotalElements())
                .totalPages(entityPage.getTotalPages())
                .hasNext(entityPage.hasNext())
                .content(
                        entityPage.getContent().stream()
                                .map(converter::toArticleResponseDtoFromEntity)
                                .sorted(Comparator.comparing(ArticleResponseDto::getArticleDeploymentDate).reversed())
                                .collect(Collectors.toList())
                ).build();
    }







}