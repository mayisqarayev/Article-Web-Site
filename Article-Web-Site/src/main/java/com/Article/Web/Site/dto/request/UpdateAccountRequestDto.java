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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateAccountRequestDto that)) return false;
        return Objects.equals(accountName, that.accountName) && Objects.equals(accountDescription, that.accountDescription) && Objects.equals(accountEmail, that.accountEmail) && Objects.equals(accountProfilePhotoUrl, that.accountProfilePhotoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountName, accountDescription, accountEmail, accountProfilePhotoUrl);
    }

    public UpdateAccountRequestDto() {
    }

    public UpdateAccountRequestDto(String accountName, String accountDescription, String accountEmail, String accountProfilePhotoUrl) {
        this.accountName = accountName;
        this.accountDescription = accountDescription;
        this.accountEmail = accountEmail;
        this.accountProfilePhotoUrl = accountProfilePhotoUrl;
    }
}
