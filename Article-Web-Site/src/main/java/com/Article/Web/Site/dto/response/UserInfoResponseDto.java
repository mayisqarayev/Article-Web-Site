package com.Article.Web.Site.dto.response;

import com.Article.Web.Site.model.AccountEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserInfoResponseDto {

    private String username;
    private String password;
    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialNonExpired;
    private String authority;
    private List<AccountResponseDto> accounts;


    public UserInfoResponseDto() {
    }

    public UserInfoResponseDto(String username, String password, boolean isEnabled, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialNonExpired, String authority, List<AccountResponseDto> accounts) {
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialNonExpired = isCredentialNonExpired;
        this.authority = authority;
        this.accounts = accounts;
    }
}
