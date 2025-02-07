package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.LikeConverter;
import com.Article.Web.Site.dto.request.LikeRequestDto;
import com.Article.Web.Site.exception.EmptyDataException;
import com.Article.Web.Site.exception.InvalidArgumentException;
import com.Article.Web.Site.exception.LikeNotFoundException;
import com.Article.Web.Site.model.LikeEntity;
import com.Article.Web.Site.repo.LikeRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository repository;
    private final LikeConverter converter;
    private final ArticleService articleService;

    public LikeService(LikeRepository repository, LikeConverter converter, @Lazy ArticleService articleService) {
        this.repository = repository;
        this.converter = converter;
        this.articleService = articleService;
    }

    public void likeArticle(LikeRequestDto requestDto) {
        Optional.ofNullable(requestDto).ifPresentOrElse(
                action -> {
                    List<LikeEntity> likes = repository.findAll();
                    likes.forEach(like -> {
                        LikeRequestDto dto = LikeRequestDto.builder()
                                .fkLikerAccountId(like.getFkLikerAccountId())
                                .fkLikedArticleId(like.getFkLikedArticleId())
                                .build();

                        if (dto.equals(requestDto) && like.getLikeStatus().equals(false)) {
                            repository.updateStatusById(like.getId());
                            return;
                        }
                    });

                    repository.save(converter.toEntityFromLikeRequestDto(requestDto));
                    articleService.increaseArticleLikeCountById(requestDto.getFkLikedArticleId());
                },
                () -> {
                    throw new InvalidArgumentException("Request is null");
                }
        );
    }

    public void unLikeArticle(String id) {
        Optional.ofNullable(id).ifPresentOrElse(
                action -> {
                    LikeEntity likeEntity = repository.unLikeArticleById(id)
                            .orElseThrow(() -> new LikeNotFoundException("Like Not Found"));
                    articleService.decreaseArticleLikeCountById(likeEntity.getFkLikedArticleId());
                },
                () -> {
                    throw new InvalidArgumentException("Id is null");
                }
        );
    }

    public void deleteLikesByLikerId(String likerId) {
        Optional.ofNullable(likerId).ifPresentOrElse(
                action -> {
                    repository.unLikesArticlesById(likerId);
                },
                () -> {
                    throw new InvalidArgumentException("LikerId is null");
                }
        );
    }

    protected List<LikeEntity> getLikes() {
        List<LikeEntity> likes = repository.findAll();
        if (likes.isEmpty()) throw new EmptyDataException("Likes is empty");
        return likes;
    }

}
