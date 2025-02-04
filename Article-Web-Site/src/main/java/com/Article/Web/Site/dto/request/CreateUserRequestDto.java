package com.Article.Web.Site.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequestDto {
    private String username;
    private String password;
    private String roleId;
}
