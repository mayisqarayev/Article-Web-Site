package com.Article.Web.Site.converter;

import com.Article.Web.Site.dto.AddArticleRequestDto;
import com.Article.Web.Site.dto.ArticleResponseDto;
import com.Article.Web.Site.model.ArticleEntity;
import com.Article.Web.Site.model.ImageEntity;
import com.Article.Web.Site.service.AccountService;
import com.Article.Web.Site.service.CategoryService;
import com.Article.Web.Site.service.ImageService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class ArticleConverter {

    private final AccountService accountService;
    private final CategoryService categoryService;

    public ArticleConverter(AccountService accountService, CategoryService categoryService) {
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    public ArticleEntity toArticleEntityFromAddArticleRequestDto(AddArticleRequestDto requestDto) {
        return ArticleEntity.builder()
                .articleHeader(requestDto.getArticleHeader())
                .articleText(requestDto.getArticleText())
                .articleDeploymentDate(LocalDate.now())
                .articleStatus(true)
                .countOfReaders(BigDecimal.ZERO)
                .countOfLikes(BigDecimal.ZERO)
                .countOfComments(BigDecimal.ZERO)
                .fkCategoryId(requestDto.getFkCategoryId())
                .fkAccountId(requestDto.getFkAccountId())
                .images(List.of()).build();
    }

    public ArticleResponseDto toArticleResponseDtoFromEntity(ArticleEntity entity) {
        return ArticleResponseDto.builder()
                .articleHeader(entity.getArticleHeader())
                .articleText(entity.getArticleText())
                .articleDeploymentDate(entity.getArticleDeploymentDate())
                .countOfReaders()
                .countOfLikes()
                .countOfComments()
                .category(categoryService.getCategoryById(entity.getFkCategoryId()))
                .account(accountService.getAccountById(entity.getFkAccountId()))
                .images(entity.getImages()).build();
    }

}
