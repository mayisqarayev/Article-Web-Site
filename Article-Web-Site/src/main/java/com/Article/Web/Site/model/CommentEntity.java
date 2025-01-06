package com.Article.Web.Site.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Entity
@Builder
@Data
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Boolean commentStatus;

    private String fkSenderAccountId;
    private String fkReceiverArticleId;

    public CommentEntity() {
    }

    public CommentEntity(String id, Boolean commentStatus, String fkSenderAccountId, String fkReceiverArticleId) {
        this.id = id;
        this.commentStatus = commentStatus;
        this.fkSenderAccountId = fkSenderAccountId;
        this.fkReceiverArticleId = fkReceiverArticleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(commentStatus, that.commentStatus) && Objects.equals(fkSenderAccountId, that.fkSenderAccountId) && Objects.equals(fkReceiverArticleId, that.fkReceiverArticleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commentStatus, fkSenderAccountId, fkReceiverArticleId);
    }
}
