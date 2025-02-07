package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.ArticleConverter;
import com.Article.Web.Site.dto.request.AddArticleRequestDto;
import com.Article.Web.Site.dto.request.AddImageRequestDto;
import com.Article.Web.Site.dto.request.ArticlePageRequestDto;
import com.Article.Web.Site.dto.request.UpdateArticleRequestDto;
import com.Article.Web.Site.dto.response.ArticleInfoResponseDto;
import com.Article.Web.Site.dto.response.ArticlePageResponseDto;
import com.Article.Web.Site.dto.response.ArticleResponseDto;
import com.Article.Web.Site.exception.ArticleNotFoundException;
import com.Article.Web.Site.exception.EmptyDataException;
import com.Article.Web.Site.exception.InvalidArgumentException;
import com.Article.Web.Site.model.ArticleEntity;
import com.Article.Web.Site.model.LikeEntity;
import com.Article.Web.Site.repo.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository repository;
    private final ArticleConverter converter;
    private final ImageService imageService;
    private final LikeService likeService;
    private final TagService tagService;

    public ArticleService(ArticleRepository repository, ArticleConverter converter, ImageService imageService, LikeService likeService, TagService tagService) {
        this.repository = repository;
        this.converter = converter;
        this.imageService = imageService;
        this.likeService = likeService;
        this.tagService = tagService;
    }

    public void addArticle(AddArticleRequestDto requestDto) {
        Optional.ofNullable(requestDto).orElseThrow(() -> new InvalidArgumentException("Request is null"));

        ArticleEntity savedEntity = repository.save(converter.toArticleEntityFromAddArticleRequestDto(requestDto));
        List<AddImageRequestDto> addedImages = requestDto.getAddedImages();
        if (addedImages.isEmpty()) throw new EmptyDataException("AddedImages is empty");

        addedImages.forEach(imageService::addImage);
        repository.updateArticleEntityImagesById(savedEntity.getId(), requestDto.getAddedImages().stream().map(imageService::transformImageEntity).collect(Collectors.toList()));

        if (requestDto.getTags().isEmpty()) throw new EmptyDataException("AddedTags is empty");
        tagService.addTags(requestDto.getTags());
    }

    public void deleteArticleById(String id) {
        Optional.ofNullable(id).ifPresentOrElse(
                action -> {
                    repository.deleteArticleEntityById(id);
                },
                () -> {
                    throw new InvalidArgumentException("Id is null");
                }
        );
    }

    public void deleteArticlesByAccountId(String accountId) {
        Optional.ofNullable(accountId).ifPresentOrElse(
                action -> {
                    repository.deleteArticlesById(accountId);
                },
                () -> {
                    throw new InvalidArgumentException("AccountId is null");
                }
        );
    }

    public void updateArticle(UpdateArticleRequestDto requestDto) {
        Optional.ofNullable(requestDto).ifPresentOrElse(
                action -> {
                    ArticleEntity entity = repository.findById(requestDto.getId())
                            .orElseThrow(() -> new ArticleNotFoundException("Article Not Found"));
                    entity.setArticleHeader(requestDto.getArticleHeader());
                    entity.setArticleText(requestDto.getArticleText());
                    entity.setArticleDeploymentDate(requestDto.getArticleDeploymentDate());
                    repository.save(entity);
                },
                () -> {
                    throw new InvalidArgumentException("Request is null");
                }
        );
    }

    private void increaseCountOfReadById(String id) {
        Optional.ofNullable(id).ifPresentOrElse(
                action -> {
                    ArticleEntity entity = repository.findById(id)
                            .orElseThrow(() -> new ArticleNotFoundException("Article Not Found"));
                    entity.setCountOfReaders(entity.getCountOfReaders().add(BigDecimal.valueOf(1)));
                    repository.save(entity);
                },
                () -> {
                    throw new InvalidArgumentException("Id is null");
                }
        );
    }

    public ArticleInfoResponseDto getArticleById(String id) {
        Optional.ofNullable(id).orElseThrow(() -> new InvalidArgumentException("Id is null"));
        return converter.toArticleInfoResponseDtoFromEntity(repository.findById(id)
                        .orElseThrow(() -> new ArticleNotFoundException("Article Not Found")))
                .apply(responseDto -> {
                    increaseCountOfReadById(id);
                    return responseDto;
                });
    }

    public List<ArticleResponseDto> getArticles() {
        List<ArticleEntity> articles = repository.findAll();
        if (articles.isEmpty()) throw new EmptyDataException("Articles is empty");

        return articles.stream()
                .map(converter::toArticleResponseDtoFromEntity)
                .sorted(Comparator.comparing(ArticleResponseDto::getArticleDeploymentDate).reversed())
                .collect(Collectors.toList());
    }

    public List<ArticleResponseDto> getLikedArticlesByAccountId(String accountId) {
        Optional.ofNullable(accountId).orElseThrow(() -> new InvalidArgumentException("AccountId is null"));
        List<LikeEntity> likes = likeService.getLikes();

        List<String> articleIds = likes.stream()
                .filter(like -> like.getFkLikerAccountId().equals(accountId))
                .map(LikeEntity::getFkLikedArticleId).distinct().toList();

        List<ArticleEntity> articles = repository.findAll();
        if (articles.isEmpty()) throw new EmptyDataException("Articles is empty");

        return articles.stream()
                .filter(entity -> articleIds.contains(entity.getId()))
                .map(converter::toArticleResponseDtoFromEntity)
                .sorted(Comparator.comparing(ArticleResponseDto::getArticleDeploymentDate).reversed())
                .collect(Collectors.toList());
    }

    public ArticlePageResponseDto getArticlesPagination(ArticlePageRequestDto requestDto) {
        Optional.ofNullable(requestDto).orElseThrow(() -> new InvalidArgumentException("Request is null"));
        Page<ArticleEntity> entityPage = repository.findAll(
                PageRequest.of(requestDto.getPageNumber(), requestDto.getPageSize())
        );

        return ArticlePageResponseDto.builder()
                .totalElements(entityPage.getTotalElements())
                .totalPages(entityPage.getTotalPages())
                .hasNext(entityPage.hasNext())
                .content(entityPage.getContent().stream()
                        .map(converter::toArticleResponseDtoFromEntity)
                        .sorted(Comparator.comparing(ArticleResponseDto::getArticleDeploymentDate).reversed())
                        .collect(Collectors.toList())
                ).build();
    }

    public List<ArticleResponseDto> getTrendingArticlesByCount(Long count) {//Algorithm = read*0.5 + like*2 + comment*3
        if (count == null || count == 0) throw new InvalidArgumentException("Count is null or zero");

        List<ArticleEntity> articles = repository.findAll();
        if (articles.isEmpty()) throw new EmptyDataException("Articles is empty");

        return articles.stream()
                .sorted(Comparator.comparing(
                        (ArticleEntity article) -> article.getCountOfReaders().multiply(BigDecimal.valueOf(0.5))
                                .add(article.getCountOfLikes().multiply(BigDecimal.valueOf(2)))
                                .add(article.getCountOfComments().multiply(BigDecimal.valueOf(3)))).reversed())
                .map(converter::toArticleResponseDtoFromEntity)
                .limit(count)
                .toList();
    }

    public List<ArticleResponseDto> getRecommendedArticlesByAccountId(String accountId) {
        Optional.ofNullable(accountId).orElseThrow(() -> new InvalidArgumentException("AccountId is null"));

        List<ArticleEntity> articles = repository.findAll();
        if (articles.isEmpty()) throw new EmptyDataException("Articles is empty");

        List<String> preferredCategories = articles.stream()
                .filter(entity -> entity.getFkAccountId().equals(accountId))
                .map(ArticleEntity::getFkCategoryId).distinct().toList();

        return articles.stream()
                .filter(entity -> preferredCategories.contains(entity.getFkCategoryId()))
                .map(converter::toArticleResponseDtoFromEntity)
                .sorted(Comparator.comparing(ArticleResponseDto::getArticleDeploymentDate).reversed())
                .collect(Collectors.toList());
    }

    public List<ArticleResponseDto> getArticlesMostReaded() {
        List<ArticleEntity> articles = repository.findAll();
        if (articles.isEmpty()) throw new EmptyDataException("Articles is empty");

        return articles.stream()
                .map(converter::toArticleResponseDtoFromEntity)
                .sorted(Comparator.comparing(ArticleResponseDto::getCountOfReaders).reversed())
                .collect(Collectors.toList());
    }

    public ArticleResponseDto getMostReadedArticle() {
        ArticleEntity entity = repository.findMostReadedArticleEntity()
                .orElseThrow(() -> new ArticleNotFoundException("Article Not Found"));

        increaseCountOfReadById(entity.getId());
        return converter.toArticleResponseDtoFromEntity(entity);
    }

    public ArticleResponseDto getMostLikedArticle() {
        ArticleEntity entity = repository.findMostLikedArticleEntity()
                .orElseThrow(() -> new ArticleNotFoundException("Article Not Found"));
        increaseCountOfReadById(entity.getId());
        return converter.toArticleResponseDtoFromEntity(entity);
    }

    public List<ArticleResponseDto> getArticlesByAccountId(String accountId) {
        Optional.ofNullable(accountId).orElseThrow(() -> new InvalidArgumentException("AccountId is null"));

        List<ArticleEntity> articles = repository.findAll();
        if (articles.isEmpty()) throw new EmptyDataException("Articles is empty");

        return articles.stream()
                .filter(entity -> entity.getFkAccountId().equals(accountId))
                .map(converter::toArticleResponseDtoFromEntity)
                .sorted(Comparator.comparing(ArticleResponseDto::getArticleDeploymentDate).reversed())
                .collect(Collectors.toList());
    }

    public ArticlePageResponseDto getArticlesPaginationByAccountId(ArticlePageRequestDto requestDto, String accountId) {
        Optional.ofNullable(requestDto).orElseThrow(() -> new InvalidArgumentException("Request is null"));
        Optional.ofNullable(accountId).orElseThrow(() -> new InvalidArgumentException("AccountId is null"));

        Page<ArticleEntity> entityPage = repository.findAll(
                PageRequest.of(requestDto.getPageNumber(), requestDto.getPageSize())
        );

        return ArticlePageResponseDto.builder()
                .totalElements(entityPage.getTotalElements())
                .totalPages(entityPage.getTotalPages())
                .hasNext(entityPage.hasNext())
                .content(entityPage.getContent().stream()
                        .filter(entity -> entity.getFkAccountId().equals(accountId))
                        .map(converter::toArticleResponseDtoFromEntity)
                        .sorted(Comparator.comparing(ArticleResponseDto::getArticleDeploymentDate).reversed())
                        .collect(Collectors.toList())
                ).build();
    }

    protected void increaseArticleCommentCountById(String id) {
        Optional.ofNullable(id).ifPresentOrElse(
                action -> {
                    repository.increaseCountOfCommentsById(id);
                },
                () -> {
                    throw new InvalidArgumentException("Id is null");
                }
        );
    }

    protected void decreaseArticleCommentCountById(String id) {
        Optional.ofNullable(id).ifPresentOrElse(
                action -> {
                    repository.decreaseArticleCommentCountById(id);
                },
                () -> {
                    throw new InvalidArgumentException("Id is null");
                }
        );
    }

    protected void increaseArticleLikeCountById(String id) {
        Optional.ofNullable(id).ifPresentOrElse(
                action -> {
                    repository.increaseArticleLikeCountById(id);
                },
                () -> {
                    throw new InvalidArgumentException("Id is null");
                }
        );
    }

    protected void decreaseArticleLikeCountById(String id) {
        Optional.ofNullable(id).ifPresentOrElse(
                action -> {
                    repository.decreaseArticleLikeCountById(id);
                },
                () -> {
                    throw new InvalidArgumentException("Id is null");
                }
        );
    }
}