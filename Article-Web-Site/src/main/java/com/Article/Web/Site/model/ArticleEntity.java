package com.Article.Web.Site.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Builder
@Data
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String articleHeader;
    private String articleText;
    private LocalDate articleDeploymentDate;
    private Boolean articleStatus;
    private BigDecimal countOfReaders;
    private BigDecimal countOfLikes;
    private BigDecimal countOfComments;

    private String fkImageId;
    private String fkCategoryId;
    private String fkAccountId;

    public ArticleEntity() {
    }

    public ArticleEntity(String id, String articleHeader, String articleText, LocalDate articleDeploymentDate, Boolean articleStatus, BigDecimal countOfReaders, BigDecimal countOfLikes, BigDecimal countOfComments, String fkImageId, String fkCategoryId, String fkAccountId) {
        this.id = id;
        this.articleHeader = articleHeader;
        this.articleText = articleText;
        this.articleDeploymentDate = articleDeploymentDate;
        this.articleStatus = articleStatus;
        this.countOfReaders = countOfReaders;
        this.countOfLikes = countOfLikes;
        this.countOfComments = countOfComments;
        this.fkImageId = fkImageId;
        this.fkCategoryId = fkCategoryId;
        this.fkAccountId = fkAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(articleHeader, that.articleHeader) && Objects.equals(articleText, that.articleText) && Objects.equals(articleDeploymentDate, that.articleDeploymentDate) && Objects.equals(articleStatus, that.articleStatus) && Objects.equals(fkImageId, that.fkImageId) && Objects.equals(fkCategoryId, that.fkCategoryId) && Objects.equals(fkAccountId, that.fkAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, articleHeader, articleText, articleDeploymentDate, articleStatus, fkImageId, fkCategoryId, fkAccountId);
    }


}
