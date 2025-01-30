package com.Article.Web.Site.converter;

import com.Article.Web.Site.dto.AccountResponseDto;
import com.Article.Web.Site.model.AccountEntity;
import com.Article.Web.Site.service.FollowService;
import jakarta.persistence.Convert;
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
