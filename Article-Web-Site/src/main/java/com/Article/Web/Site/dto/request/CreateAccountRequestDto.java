package com.Article.Web.Site.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAccountRequestDto {

    private String username;
    private String password;
    private String roleId;

    public CreateAccountRequestDto() {
    }

    public CreateAccountRequestDto(String username, String password, String roleId) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }
}
