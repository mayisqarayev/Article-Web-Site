package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.ArticleConverter;
import com.Article.Web.Site.dto.request.AddArticleRequestDto;
import com.Article.Web.Site.dto.request.ArticlePageRequestDto;
import com.Article.Web.Site.dto.request.UpdateArticleRequestDto;
import com.Article.Web.Site.dto.response.ArticleInfoResponseDto;
import com.Article.Web.Site.dto.response.ArticlePageResponseDto;
import com.Article.Web.Site.dto.response.ArticleResponseDto;
import com.Article.Web.Site.model.ArticleEntity;
import com.Article.Web.Site.model.LikeEntity;
import com.Article.Web.Site.repo.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository repository;
    private final ArticleConverter converter;
    private final ImageService imageService;
    private final LikeService likeService;

    public ArticleService(ArticleRepository repository, ArticleConverter converter, ImageService imageService, LikeService likeService) {
        this.repository = repository;
        this.converter = converter;
        this.imageService = imageService;
        this.likeService = likeService;
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

    public void updateArticle(UpdateArticleRequestDto requestDto) {
        ArticleEntity entity = repository.findById(requestDto.getId()).get();
        entity.setArticleHeader(requestDto.getArticleHeader());
        entity.setArticleText(requestDto.getArticleText());
        entity.setArticleDeploymentDate(requestDto.getArticleDeploymentDate());
        repository.save(entity);
    }

    private void increaseCountOfReadById(String id) {
        ArticleEntity entity = repository.findById(id).get();
        entity.setCountOfReaders(entity.getCountOfReaders().add(BigDecimal.valueOf(1)));
        repository.save(entity);
    }

    public ArticleInfoResponseDto getArticleById(String id) {
        return converter.toArticleInfoResponseDtoFromEntity(repository.findById(id).get())
                .apply(responseDto -> {
                    increaseCountOfReadById(id);
                    return responseDto;
                });
    }

    public List<ArticleResponseDto> getArticles() {
        return repository.findAll().stream()
                .map(converter::toArticleResponseDtoFromEntity)
                .sorted(Comparator.comparing(ArticleResponseDto::getArticleDeploymentDate).reversed())
                .collect(Collectors.toList());
    }

    public List<ArticleResponseDto> getLikedArticlesByAccountId(String accountId) {
        List<LikeEntity> likes = likeService.getLikes();

        List<String> articleIds = likes.stream()
                .filter(like -> like.getFkLikerAccountId().equals(accountId))
                .map(LikeEntity::getFkLikedArticleId)
                .distinct().toList();

        return repository.findAll().stream()
                .filter(entity -> articleIds.contains(entity.getId()))
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

    public List<ArticleResponseDto> getTrendingArticlesByCount(Long count) {//Algorithm = read*0.5 + like*2 + comment*3
        return repository.findAll().stream()
                .sorted(Comparator.comparing((ArticleEntity article) ->
                        article.getCountOfReaders().multiply(BigDecimal.valueOf(0.5))
                                .add(article.getCountOfLikes().multiply(BigDecimal.valueOf(2)))
                                .add(article.getCountOfComments().multiply(BigDecimal.valueOf(3)))).reversed())
                .map(converter::toArticleResponseDtoFromEntity)
                .limit(count)
                .toList();
    }

    public List<ArticleResponseDto> getRecommendedArticlesByAccountId(String accountId) {
        List<String> preferredCategories = repository.findAll().stream()
                .filter(entity -> entity.getFkAccountId().equals(accountId))
                .map(ArticleEntity::getFkCategoryId)
                .distinct().toList();

        return repository.findAll().stream()
                .filter(entity -> preferredCategories.contains(entity.getFkCategoryId()))
                .map(converter::toArticleResponseDtoFromEntity)
                .sorted(Comparator.comparing(ArticleResponseDto::getArticleDeploymentDate).reversed())
                .collect(Collectors.toList());
    }

    public List<ArticleResponseDto> getArticlesMostReaded() {
        return repository.findAll().stream()
                .map(converter::toArticleResponseDtoFromEntity)
                .sorted(Comparator.comparing(ArticleResponseDto::getCountOfReaders).reversed())
                .collect(Collectors.toList());
    }

    public ArticleResponseDto getMostReadedArticle() {
        ArticleEntity entity = repository.findMostReadedArticleEntity().get();
        increaseCountOfReadById(entity.getId());
        return converter.toArticleResponseDtoFromEntity(entity);
    }

    public ArticleResponseDto getMostLikedArticle() {
        ArticleEntity entity = repository.findMostLikedArticleEntity().get();
        increaseCountOfReadById(entity.getId());
        return converter.toArticleResponseDtoFromEntity(entity);
    }

    public List<ArticleResponseDto> getArticlesByAccountId(String accountId) {
        return repository.findAll().stream()
                .filter(entity -> entity.getFkAccountId().equals(accountId))
                .map(converter::toArticleResponseDtoFromEntity)
                .sorted(Comparator.comparing(ArticleResponseDto::getArticleDeploymentDate).reversed())
                .collect(Collectors.toList());
    }

    public ArticlePageResponseDto getArticlesPaginationByAccountId(ArticlePageRequestDto requestDto, String accountId) {
        Page<ArticleEntity> entityPage = repository.findAll(PageRequest.of(
                requestDto.getPageNumber(), requestDto.getPageSize()
        ));
        return ArticlePageResponseDto.builder()
                .totalElements(entityPage.getTotalElements())
                .totalPages(entityPage.getTotalPages())
                .hasNext(entityPage.hasNext())
                .content(
                        entityPage.getContent().stream()
                                .filter(entity -> entity.getFkAccountId().equals(accountId))
                                .map(converter::toArticleResponseDtoFromEntity)
                                .sorted(Comparator.comparing(ArticleResponseDto::getArticleDeploymentDate).reversed())
                                .collect(Collectors.toList())
                ).build();
    }

    protected void increaseArticleCommentCountById(String id) {
        repository.increaseCountOfCommentsById(id);
    }

    protected void decreaseArticleCommentCountById(String id) {
        repository.decreaseArticleCommentCountById(id);
    }

    protected void increaseArticleLikeCountById(String id) {
        repository.increaseArticleLikeCountById(id);
    }

    protected void decreaseArticleLikeCountById(String id) {
        repository.decreaseArticleLikeCountById(id);
    }
}