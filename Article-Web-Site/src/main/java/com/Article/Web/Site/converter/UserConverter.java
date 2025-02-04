package com.Article.Web.Site.converter;

import com.Article.Web.Site.dto.request.CreateUserRequestDto;
import com.Article.Web.Site.model.UserEntity;
import com.Article.Web.Site.service.RoleService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private final RoleService roleService;

    public UserConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    public UserEntity toEntityFromCreateUserRequestDto(CreateUserRequestDto requestDto) {
        return UserEntity.builder()
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialNonExpired(true)
                .authority(roleService.getRoleNameById(requestDto.getRoleId())).build();
    }
}
