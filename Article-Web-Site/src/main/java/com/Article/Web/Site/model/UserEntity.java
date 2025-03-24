package com.Article.Web.Site.model;

import com.Article.Web.Site.service.LikeService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String password;
    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private String authority;

    @OneToMany(mappedBy = "fkUserId", targetEntity = AccountEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountEntity> accounts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return isEnabled == that.isEnabled && isAccountNonExpired == that.isAccountNonExpired && isAccountNonLocked == that.isAccountNonLocked && isCredentialsNonExpired == that.isCredentialsNonExpired && Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(authority, that.authority) && Objects.equals(accounts, that.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, isEnabled, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, authority, accounts);
    }
}
