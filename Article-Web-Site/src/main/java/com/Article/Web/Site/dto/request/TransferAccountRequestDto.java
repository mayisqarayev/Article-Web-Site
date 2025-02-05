package com.Article.Web.Site.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferAccountRequestDto {

    private String accountId;
    private String currentUserId;
    private String newUserId;

    public TransferAccountRequestDto() {
    }

    public TransferAccountRequestDto(String accountId, String currentUserId, String newUserId) {
        this.accountId = accountId;
        this.currentUserId = currentUserId;
        this.newUserId = newUserId;
    }
}
