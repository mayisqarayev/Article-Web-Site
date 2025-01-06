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
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String imageUrl;
    private Boolean imageStatus;

    public ImageEntity() {
    }

    public ImageEntity(String id, String imageUrl, Boolean imageStatus) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.imageStatus = imageStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(imageUrl, that.imageUrl) && Objects.equals(imageStatus, that.imageStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageUrl, imageStatus);
    }
}
