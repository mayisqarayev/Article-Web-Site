package com.Article.Web.Site.controller;

import com.Article.Web.Site.dto.request.AddArticleRequestDto;
import com.Article.Web.Site.dto.request.ArticlePageRequestDto;
import com.Article.Web.Site.dto.request.UpdateArticleRequestDto;
import com.Article.Web.Site.dto.response.ArticleInfoResponseDto;
import com.Article.Web.Site.dto.response.ArticlePageResponseDto;
import com.Article.Web.Site.dto.response.ArticleResponseDto;
import com.Article.Web.Site.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/article/v1")
public class ArticleController {

    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @PostMapping("/add-article")
    @ResponseStatus(HttpStatus.CREATED)
    public void addArticle(@RequestBody AddArticleRequestDto requestDto) {
        service.addArticle(requestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete-article/{id}")
    public void deleteArticleById(@PathVariable String id) {
        service.deleteArticleById(id);
    }

    @DeleteMapping("/delete-articleby-account-id/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteArticlesByAccountId(@PathVariable String accountId) {
        service.deleteArticlesByAccountId(accountId);
    }

    @PutMapping("/update-article")
    @ResponseStatus(HttpStatus.OK)
    public void updateArticle(@RequestBody UpdateArticleRequestDto requestDto) {
        service.updateArticle(requestDto);
    }

    @GetMapping("/article/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArticleInfoResponseDto getArticleById(@PathVariable String id) {
        return service.getArticleById(id);
    }

    @GetMapping("/articles")
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleResponseDto> getArticles() {
        return service.getArticles();
    }

    @GetMapping("/liked-articles/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleResponseDto> getLikedArticlesByAccountId(@PathVariable String accountId) {
       return service.getLikedArticlesByAccountId(accountId);
    }

    @GetMapping("/articles/pagination")
    @ResponseStatus(HttpStatus.OK)
    public ArticlePageResponseDto getArticlesPagination(@RequestBody ArticlePageRequestDto requestDto) {
        return service.getArticlesPagination(requestDto);
    }

    @GetMapping("/trend-articles/{count}")
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleResponseDto> getTrendingArticlesByCount(@PathVariable Long count) {
        return service.getTrendingArticlesByCount(count);
    }

    @GetMapping("/recommended-articlesby-account-id/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleResponseDto> getRecommendedArticlesByAccountId(@PathVariable String accountId) {
        return service.getRecommendedArticlesByAccountId(accountId);
    }

    @GetMapping("/most-readed-articles")
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleResponseDto> getArticlesMostReaded() {
        return service.getArticlesMostReaded();
    }

    @GetMapping("/most-readed-article")
    @ResponseStatus(HttpStatus.OK)
    public ArticleResponseDto getMostReadedArticle() {
        return service.getMostReadedArticle();
    }

    @GetMapping("/most-liked-article")
    @ResponseStatus(HttpStatus.OK)
    public ArticleResponseDto getMostLikedArticle() {
        return service.getMostLikedArticle();
    }

    @GetMapping("/articles/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleResponseDto> getArticlesByAccountId(@PathVariable String accountId) {
        return service.getArticlesByAccountId(accountId);
    }

    @GetMapping("/articles/pagination/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public ArticlePageResponseDto getArticlesPaginationByAccountId(
            @RequestBody ArticlePageRequestDto requestDto,
            @PathVariable String accountId
    ) {
        return service.getArticlesPaginationByAccountId(requestDto, accountId);
    }
}
