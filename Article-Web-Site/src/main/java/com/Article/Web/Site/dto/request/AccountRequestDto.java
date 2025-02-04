package com.Article.Web.Site.dto.request;

import com.Article.Web.Site.model.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountRequestDto {

    CreateAccountRequestDto requestDto;
    UserEntity user;
}
