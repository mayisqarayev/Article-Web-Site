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
public class UserResponseDto {

    private String username;
    private String authority;
    private List<AccountResponseDto> accounts;

    public UserResponseDto() {
    }

    public UserResponseDto(String username, String authority, List<AccountResponseDto> accounts) {
        this.username = username;
        this.authority = authority;
        this.accounts = accounts;
    }
}
