package com.Article.Web.Site.service;

import com.Article.Web.Site.repo.FollowRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FollowService {

    private final FollowRepository repository;

    public FollowService(FollowRepository repository) {
        this.repository = repository;
    }

    public BigDecimal getTotalFollowersById(String id) {
        return repository.calculateTotalFollowersById(id);
    }

}
