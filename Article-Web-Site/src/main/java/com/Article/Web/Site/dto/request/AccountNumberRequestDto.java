package com.Article.Web.Site.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountNumberRequestDto {

    private String number;
    private String accountId;

}
