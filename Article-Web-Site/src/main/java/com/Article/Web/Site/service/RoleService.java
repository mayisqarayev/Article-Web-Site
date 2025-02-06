package com.Article.Web.Site.service;

import com.Article.Web.Site.dto.request.AddRoleRequestDto;
import com.Article.Web.Site.model.RoleEntity;
import com.Article.Web.Site.repo.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public String getRoleNameById(String id) {
        return repository.findById(id).get().getRoleName();
    }

    public List<String> getRoles() {
        return repository.findAll().stream()
                .map(RoleEntity::getRoleName)
                .collect(Collectors.toList());
    }

    public void addRole(AddRoleRequestDto requestDto) {
        repository.save(
                RoleEntity.builder()
                        .roleName(requestDto.getRoleName())
                        .roleStatus(true)
                        .build());
    }

    public void deleteRoleById(String id) {
        repository.deleteRoleById(id);
    }
}
