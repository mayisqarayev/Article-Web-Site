package com.Article.Web.Site.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class AccountInfoResponseDto {

    private String accountName;
    private String accountDescription;
    private String accountEmail;
    private LocalDate accountCreationDate;
    private BigDecimal countOfFollowers;
    private String accountNumber;
    private String accountProfilePhotoUrl;

    public AccountInfoResponseDto() {
    }

    public AccountInfoResponseDto(String accountName, String accountDescription, String accountEmail, LocalDate accountCreationDate, BigDecimal countOfFollowers, String accountNumber, String accountProfilePhotoUrl) {
        this.accountName = accountName;
        this.accountDescription = accountDescription;
        this.accountEmail = accountEmail;
        this.accountCreationDate = accountCreationDate;
        this.countOfFollowers = countOfFollowers;
        this.accountNumber = accountNumber;
        this.accountProfilePhotoUrl = accountProfilePhotoUrl;
    }
}
