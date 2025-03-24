package com.Article.Web.Site.converter;

import com.Article.Web.Site.dto.request.CreateUserRequestDto;
import com.Article.Web.Site.dto.response.AccountResponseDto;
import com.Article.Web.Site.dto.response.UserInfoResponseDto;
import com.Article.Web.Site.dto.response.UserResponseDto;
import com.Article.Web.Site.model.UserEntity;
import com.Article.Web.Site.service.RoleService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConverter {

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    public UserConverter(RoleService roleService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    public UserResponseDto toUserResponseDtoFromEntity(UserEntity entity) {
        return UserResponseDto.builder()
                .username(entity.getUsername())
                .authority(entity.getAuthority())
                .accounts(
                        entity.getAccounts().stream()
                                .map(account ->
                                        AccountResponseDto.builder()
                                                .accountName(account.getAccountName())
                                                .countOfFollowers(account.getCountOfFollowers())
                                                .photoUrl(account.getAccountProfilePhotoUrl()).build()
                                ).collect(Collectors.toList())
                ).build();
    }
    public UserEntity toEntityFromCreateUserRequestDto(CreateUserRequestDto requestDto) {
        return UserEntity.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .authority(roleService.getRoleNameById(requestDto.getRoleId())).build();
    }

    public UserInfoResponseDto toUserInfoResponseDtoFromEntity(UserEntity entity) {
        return UserInfoResponseDto.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .isEnabled(entity.isEnabled())
                .isAccountNonExpired(entity.isAccountNonExpired())
                .isAccountNonLocked(entity.isAccountNonLocked())
                .isCredentialNonExpired(entity.isCredentialsNonExpired())
                .authority(entity.getAuthority())
                .accounts(entity.getAccounts().stream()
                        .map(account ->
                                AccountResponseDto.builder()
                                        .accountName(account.getAccountName())
                                        .countOfFollowers(account.getCountOfFollowers())
                                        .photoUrl(account.getAccountProfilePhotoUrl()).build()
                        ).collect(Collectors.toList())
                ).build();
    }
}
