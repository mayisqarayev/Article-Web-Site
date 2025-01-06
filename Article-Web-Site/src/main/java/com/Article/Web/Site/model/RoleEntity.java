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
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String roleName;
    private Boolean roleStatus;

    public RoleEntity() {
    }

    public RoleEntity(String id, String roleName, Boolean roleStatus) {
        this.id = id;
        this.roleName = roleName;
        this.roleStatus = roleStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(roleName, that.roleName) && Objects.equals(roleStatus, that.roleStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName, roleStatus);
    }
}
