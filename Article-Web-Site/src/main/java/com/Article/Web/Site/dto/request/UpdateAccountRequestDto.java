package com.Article.Web.Site.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Data
@Builder
public class UpdateAccountRequestDto {

    private String id;
    private String accountName;
    private String accountDescription;
    private String accountEmail;
    private String accountProfilePhotoUrl;

    public UpdateAccountRequestDto() {
    }

    public UpdateAccountRequestDto(String id, String accountName, String accountDescription, String accountEmail, String accountProfilePhotoUrl) {
        this.id = id;
        this.accountName = accountName;
        this.accountDescription = accountDescription;
        this.accountEmail = accountEmail;
        this.accountProfilePhotoUrl = accountProfilePhotoUrl;
    }
}
