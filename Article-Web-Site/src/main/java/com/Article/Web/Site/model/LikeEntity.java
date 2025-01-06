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
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Boolean likeStatus;

    private String fkLikerAccountId;
    private String fkLikedArticleId;

    public LikeEntity() {
    }

    public LikeEntity(String id, Boolean likeStatus, String fkLikerAccountId, String fkLikedArticleId) {
        this.id = id;
        this.likeStatus = likeStatus;
        this.fkLikerAccountId = fkLikerAccountId;
        this.fkLikedArticleId = fkLikedArticleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikeEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(likeStatus, that.likeStatus) && Objects.equals(fkLikerAccountId, that.fkLikerAccountId) && Objects.equals(fkLikedArticleId, that.fkLikedArticleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, likeStatus, fkLikerAccountId, fkLikedArticleId);
    }
}
