package com.Article.Web.Site.service;

import com.Article.Web.Site.dto.request.AddRoleRequestDto;
import com.Article.Web.Site.exception.EmptyDataException;
import com.Article.Web.Site.exception.InvalidArgumentException;
import com.Article.Web.Site.exception.RoleNotFoundException;
import com.Article.Web.Site.model.RoleEntity;
import com.Article.Web.Site.repo.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public String getRoleNameById(String id) {
        Optional.ofNullable(id).orElseThrow(() -> new InvalidArgumentException("Id is null"));
        return repository.findById(id).orElseThrow(() -> new RoleNotFoundException("Role Not Found")).getRoleName();
    }

    public List<String> getRoles() {
        List<RoleEntity> roles = repository.findAll();
        if(roles.isEmpty()) throw new EmptyDataException("Roles is empty");
        return roles.stream()
                .map(RoleEntity::getRoleName)
                .collect(Collectors.toList());
    }

    public void addRole(AddRoleRequestDto requestDto) {
        Optional.ofNullable(requestDto).ifPresentOrElse(
                action -> {
                    repository.save(
                            RoleEntity.builder()
                                    .roleName(requestDto.getRoleName())
                                    .roleStatus(true)
                                    .build());
                },
                () -> {
                    throw new InvalidArgumentException("Request is null");
                }
        );
    }

    public void deleteRoleById(String id) {
        Optional.ofNullable(id).ifPresentOrElse(
                action -> {
                    repository.deleteRoleById(id);
                },
                () -> {
                    throw new InvalidArgumentException("Id is null");
                }
        );
    }
}
