package com.Article.Web.Site.service;

import com.Article.Web.Site.model.UserEntity;
import com.Article.Web.Site.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }


}
