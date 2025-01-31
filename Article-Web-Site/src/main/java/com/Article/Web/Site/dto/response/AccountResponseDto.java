package com.Article.Web.Site.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class AccountResponseDto {

    private String accountName;
    private BigDecimal countOfFollowers;
    private String photoUrl;
}
