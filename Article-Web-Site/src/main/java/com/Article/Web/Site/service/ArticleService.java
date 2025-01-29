package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.ArticleConverter;
import com.Article.Web.Site.dto.AddArticleRequestDto;
import com.Article.Web.Site.dto.ArticleResponseDto;
import com.Article.Web.Site.model.ArticleEntity;
import com.Article.Web.Site.repo.ArticleRepository;
import org.springframework.stereotype.Service;

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

    public ArticleResponseDto getArticleById(String id) {
        return repository.findById(id).get();
    }



}