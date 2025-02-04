package com.Article.Web.Site.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CreateAccountRequestDto {

    private String accountName;
    private String accountEmail;
    private String accountProfilePhotoUrl;
    private String fkUserId;

    public CreateAccountRequestDto() {
    }

    public CreateAccountRequestDto(String accountName, String accountEmail, String accountProfilePhotoUrl, String fkUserId) {
        this.accountName = accountName;
        this.accountEmail = accountEmail;
        this.accountProfilePhotoUrl = accountProfilePhotoUrl;
        this.fkUserId = fkUserId;
    }
}
