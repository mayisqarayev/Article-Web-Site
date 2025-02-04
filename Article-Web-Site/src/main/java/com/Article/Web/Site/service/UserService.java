package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.UserConverter;
import com.Article.Web.Site.dto.request.CreateUserRequestDto;
import com.Article.Web.Site.exception.UserNotFoundException;
import com.Article.Web.Site.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserConverter converter;

    public UserService(UserRepository repository, UserConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public void createUser(CreateUserRequestDto requestDto) {
        repository.save(converter.toEntityFromCreateUserRequestDto(requestDto));
    }

    protected void validateUserById(String id) {
        repository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }
}
