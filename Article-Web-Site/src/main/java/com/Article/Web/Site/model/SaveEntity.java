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
public class SaveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Boolean saveStatus;

    private String fkSaverAccountId;
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
