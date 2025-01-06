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
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String tagName;
    private Boolean tagStatus;

    public TagEntity() {
    }

    public TagEntity(String id, String tagName, Boolean tagStatus) {
        this.id = id;
        this.tagName = tagName;
        this.tagStatus = tagStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagEntity tagEntity)) return false;
        return Objects.equals(id, tagEntity.id) && Objects.equals(tagName, tagEntity.tagName) && Objects.equals(tagStatus, tagEntity.tagStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tagName, tagStatus);
    }
}
