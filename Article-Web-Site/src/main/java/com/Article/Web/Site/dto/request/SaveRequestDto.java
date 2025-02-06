package com.Article.Web.Site.dto.request;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveRequestDto {

    private Boolean saveStatus;
    private String fkSaverAccountId;
    private String fkSavedArticleId;

    public SaveRequestDto() {
    }

    public SaveRequestDto(Boolean saveStatus, String fkSaverAccountId, String fkSavedArticleId) {
        this.saveStatus = saveStatus;
        this.fkSaverAccountId = fkSaverAccountId;
        this.fkSavedArticleId = fkSavedArticleId;
    }
}
