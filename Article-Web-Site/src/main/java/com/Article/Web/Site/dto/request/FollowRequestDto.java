package com.Article.Web.Site.dto.request;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowRequestDto {

    private String fkFollowerAccountId;
    private String fkFollowedAccountId;

    public FollowRequestDto() {
    }

    public FollowRequestDto(String fkFollowerAccountId, String fkFollowedAccountId) {
        this.fkFollowerAccountId = fkFollowerAccountId;
        this.fkFollowedAccountId = fkFollowedAccountId;
    }
}
