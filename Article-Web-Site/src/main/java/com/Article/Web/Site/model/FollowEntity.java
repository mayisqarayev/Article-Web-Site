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
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Boolean followStatus;

    private String fkFollowerAccountId;
    private String fkFollowedAccountId;

    public FollowEntity() {
    }

    public FollowEntity(String id, Boolean followStatus, String fkFollowerAccountId, String fkFollowedAccountId) {
        this.id = id;
        this.followStatus = followStatus;
        this.fkFollowerAccountId = fkFollowerAccountId;
        this.fkFollowedAccountId = fkFollowedAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FollowEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(followStatus, that.followStatus) && Objects.equals(fkFollowerAccountId, that.fkFollowerAccountId) && Objects.equals(fkFollowedAccountId, that.fkFollowedAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, followStatus, fkFollowerAccountId, fkFollowedAccountId);
    }
}
