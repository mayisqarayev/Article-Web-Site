package com.Article.Web.Site.converter;

import com.Article.Web.Site.dto.request.AccountNumberRequestDto;
import com.Article.Web.Site.model.AccountNumberEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountNumberConverter {
    public AccountNumberEntity toEntityFromAccountNumberRequestDto(AccountNumberRequestDto requestDto) {
        return AccountNumberEntity.builder()
                .numberOfAccount(requestDto.getNumber())
                .fkAccountId(requestDto.getAccountId())
                .build();
    }
}
