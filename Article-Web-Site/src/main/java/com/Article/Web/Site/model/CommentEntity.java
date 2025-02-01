package com.Article.Web.Site.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Builder
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {
        "comment_status", "fk_sender_account_id", "fk_receiver_article_id"
})})
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String commentText;
    private LocalDateTime deployDate;

    @Column(name = "comment_status")
    private Boolean commentStatus;

    @Column(name = "fk_sender_account_id")
    private String fkSenderAccountId;

    @Column(name = "fk_receiver_article_id")
    private String fkReceiverArticleId;

    public CommentEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(commentText, that.commentText) && Objects.equals(deployDate, that.deployDate) && Objects.equals(commentStatus, that.commentStatus) && Objects.equals(fkSenderAccountId, that.fkSenderAccountId) && Objects.equals(fkReceiverArticleId, that.fkReceiverArticleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commentText, deployDate, commentStatus, fkSenderAccountId, fkReceiverArticleId);
    }

    public CommentEntity(String id, String commentText, LocalDateTime deployDate, Boolean commentStatus, String fkSenderAccountId, String fkReceiverArticleId) {
        this.id = id;
        this.commentText = commentText;
        this.deployDate = deployDate;
        this.commentStatus = commentStatus;
        this.fkSenderAccountId = fkSenderAccountId;
        this.fkReceiverArticleId = fkReceiverArticleId;
    }
}
