package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.ArticleConverter;
import com.Article.Web.Site.repo.ArticleRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository repository;
    private final ArticleConverter converter;

    public ArticleService(ArticleRepository repository, ArticleConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }
/*
    public ArticleResponseDto getArticleById(String id) {
        return
    }
*/




}