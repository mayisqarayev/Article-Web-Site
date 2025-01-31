package com.Article.Web.Site.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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

    private String fkCategoryId;
    private String fkAccountId;
    @OneToMany(mappedBy = "fkArticleId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = ImageEntity.class)
    private List<ImageEntity> images;

    public ArticleEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(articleHeader, that.articleHeader) && Objects.equals(articleText, that.articleText) && Objects.equals(articleDeploymentDate, that.articleDeploymentDate) && Objects.equals(articleStatus, that.articleStatus) && Objects.equals(countOfReaders, that.countOfReaders) && Objects.equals(countOfLikes, that.countOfLikes) && Objects.equals(countOfComments, that.countOfComments) && Objects.equals(fkCategoryId, that.fkCategoryId) && Objects.equals(fkAccountId, that.fkAccountId) && Objects.equals(images, that.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, articleHeader, articleText, articleDeploymentDate, articleStatus, countOfReaders, countOfLikes, countOfComments, fkCategoryId, fkAccountId, images);
    }

    public ArticleEntity(String id, String articleHeader, String articleText, LocalDate articleDeploymentDate, Boolean articleStatus, BigDecimal countOfReaders, BigDecimal countOfLikes, BigDecimal countOfComments, String fkCategoryId, String fkAccountId, List<ImageEntity> images) {
        this.id = id;
        this.articleHeader = articleHeader;
        this.articleText = articleText;
        this.articleDeploymentDate = articleDeploymentDate;
        this.articleStatus = articleStatus;
        this.countOfReaders = countOfReaders;
        this.countOfLikes = countOfLikes;
        this.countOfComments = countOfComments;
        this.fkCategoryId = fkCategoryId;
        this.fkAccountId = fkAccountId;
        this.images = images;
    }
}
