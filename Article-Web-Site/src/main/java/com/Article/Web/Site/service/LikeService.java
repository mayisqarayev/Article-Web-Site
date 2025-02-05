package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.LikeConverter;
import com.Article.Web.Site.dto.request.LikeRequestDto;
import com.Article.Web.Site.model.LikeEntity;
import com.Article.Web.Site.repo.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    private final LikeRepository repository;
    private final LikeConverter converter;
    private final ArticleService articleService;

    public LikeService(LikeRepository repository, LikeConverter converter, ArticleService articleService) {
        this.repository = repository;
        this.converter = converter;
        this.articleService = articleService;
    }

    public void likeArticle(LikeRequestDto requestDto) {

        List<LikeEntity> likes = repository.findAll();
        likes.forEach(like -> {
            LikeRequestDto dto = LikeRequestDto.builder()
                    .fkLikerAccountId(like.getFkLikerAccountId())
                    .fkLikedArticleId(like.getFkLikedArticleId())
                    .build();

            if(dto.equals(requestDto) && like.getLikeStatus().equals(false)) {
                repository.updateStatusById(like.getId()); return;
            }
        });

        repository.save(converter.toEntityFromLikeRequestDto(requestDto));
        articleService.increaseArticleLikeCountById(requestDto.getFkLikedArticleId());
    }

    public void unLikeArticle(String id) {
        LikeEntity likeEntity = repository.unLikeArticleById(id).get();
        articleService.decreaseArticleLikeCountById(likeEntity.getFkLikedArticleId());
    }

    public void deleteLikesByLikerId(String likerId) {
        repository.unLikesArticlesById(likerId);
    }

    protected List<LikeEntity> getLikes() {
        return repository.findAll();
    }

}
