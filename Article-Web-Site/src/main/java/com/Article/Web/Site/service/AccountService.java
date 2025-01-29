package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.AccountConverter;
import com.Article.Web.Site.dto.AccountResponseDto;
import com.Article.Web.Site.repo.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository repository;
    private final AccountConverter converter;

    public AccountService(AccountRepository repository, AccountConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public AccountResponseDto getAccountById(String id) {
        return converter.toAccountResponseDtoFromEntity(repository.findById(id).get());
    }


}
