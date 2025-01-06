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
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String categoryName;
    private Boolean categoryStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(categoryName, that.categoryName) && Objects.equals(categoryStatus, that.categoryStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryName, categoryStatus);
    }

    public CategoryEntity() {
    }

    public CategoryEntity(String id, String categoryName, Boolean categoryStatus) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryStatus = categoryStatus;
    }
}
