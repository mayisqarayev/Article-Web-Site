package com.Article.Web.Site.converter;

import com.Article.Web.Site.dto.response.AccountResponseDto;
import com.Article.Web.Site.model.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {
    public AccountResponseDto toAccountResponseDtoFromEntity(AccountEntity entity) {
        return AccountResponseDto.builder()
                .accountName(entity.getAccountName())
                .countOfFollowers(entity.getCountOfFollowers())
                .photoUrl(entity.getFkAccountProfilePhotoUrl()).build();
    }

}
