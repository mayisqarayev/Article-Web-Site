package com.Article.Web.Site.converter;

import com.Article.Web.Site.dto.request.AccountRequestDto;
import com.Article.Web.Site.dto.request.CreateAccountRequestDto;
import com.Article.Web.Site.dto.response.AccountResponseDto;
import com.Article.Web.Site.model.AccountEntity;
import com.Article.Web.Site.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class AccountConverter {

    private final AccountService accountService;

    public AccountConverter(AccountService accountService) {
        this.accountService = accountService;
    }

    public AccountResponseDto toAccountResponseDtoFromEntity(AccountEntity entity) {
        return AccountResponseDto.builder()
                .accountName(entity.getAccountName())
                .countOfFollowers(entity.getCountOfFollowers())
                .photoUrl(entity.getAccountProfilePhotoUrl()).build();
    }



}
