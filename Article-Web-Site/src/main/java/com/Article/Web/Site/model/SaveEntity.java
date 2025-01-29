package com.Article.Web.Site.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Entity
@Builder
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {
        "save_status", "fk_saver_account_id", "fk_saved_article_id"
})})
public class SaveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "save_status")
    private Boolean saveStatus;

    @Column(name = "fk_saver_account_id")
    private String fkSaverAccountId;

    @Column(name = "fk_saved_article_id")
    private String fkSavedArticleId;

    public SaveEntity() {
    }

    public SaveEntity(String id, Boolean saveStatus, String fkSaverAccountId, String fkSavedArticleId) {
        this.id = id;
        this.saveStatus = saveStatus;
        this.fkSaverAccountId = fkSaverAccountId;
        this.fkSavedArticleId = fkSavedArticleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaveEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(saveStatus, that.saveStatus) && Objects.equals(fkSaverAccountId, that.fkSaverAccountId) && Objects.equals(fkSavedArticleId, that.fkSavedArticleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, saveStatus, fkSaverAccountId, fkSavedArticleId);
    }
}
