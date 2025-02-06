package com.Article.Web.Site.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddRoleRequestDto {

    private String roleName;

    public AddRoleRequestDto() {
    }

    public AddRoleRequestDto(String roleName) {
        this.roleName = roleName;
    }
}
