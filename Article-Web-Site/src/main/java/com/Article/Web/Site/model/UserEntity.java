package com.Article.Web.Site.model;

import com.Article.Web.Site.service.LikeService;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Entity
@Builder
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String password;
    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialNonExpired;
    private String authority;

    @OneToMany(mappedBy = "fkUserId", targetEntity = AccountEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountEntity> accounts;

    public UserEntity() {
    }

    public UserEntity(String id, String username, String password, boolean isEnabled, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialNonExpired, String authority) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialNonExpired = isCredentialNonExpired;
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return isEnabled == that.isEnabled && isAccountNonExpired == that.isAccountNonExpired && isAccountNonLocked == that.isAccountNonLocked && isCredentialNonExpired == that.isCredentialNonExpired && Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, isEnabled, isAccountNonExpired, isAccountNonLocked, isCredentialNonExpired, authority);
    }
}
