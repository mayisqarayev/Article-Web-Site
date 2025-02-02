package com.Article.Web.Site.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Builder
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {
        "follow_status", "fk_follower_account_id", "fk_followed_account_id"
})})
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private LocalDateTime followDate;

    @Column(name = "follow_status")
    private Boolean followStatus;

    @Column(name = "fk_follower_account_id")
    private String fkFollowerAccountId;

    @Column(name = "fk_followed_account_id")
    private String fkFollowedAccountId;

    public FollowEntity() {
    }

    public FollowEntity(String id, LocalDateTime followDate, Boolean followStatus, String fkFollowerAccountId, String fkFollowedAccountId) {
        this.id = id;
        this.followDate = followDate;
        this.followStatus = followStatus;
        this.fkFollowerAccountId = fkFollowerAccountId;
        this.fkFollowedAccountId = fkFollowedAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FollowEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(followDate, that.followDate) && Objects.equals(followStatus, that.followStatus) && Objects.equals(fkFollowerAccountId, that.fkFollowerAccountId) && Objects.equals(fkFollowedAccountId, that.fkFollowedAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, followDate, followStatus, fkFollowerAccountId, fkFollowedAccountId);
    }
}
