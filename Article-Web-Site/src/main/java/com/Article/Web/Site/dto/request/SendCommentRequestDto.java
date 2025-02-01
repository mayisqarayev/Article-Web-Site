package com.Article.Web.Site.dto.request;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendCommentRequestDto {

    private String commentText;
    private String fkSenderAccountId;
    private String fkReceiverArticleId;

    public SendCommentRequestDto() {

    }

    public SendCommentRequestDto(String commentText, String fkSenderAccountId, String fkReceiverArticleId) {
        this.commentText = commentText;
        this.fkSenderAccountId = fkSenderAccountId;
        this.fkReceiverArticleId = fkReceiverArticleId;
    }
}
