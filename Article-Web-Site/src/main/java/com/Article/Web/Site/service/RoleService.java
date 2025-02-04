package com.Article.Web.Site.service;

import com.Article.Web.Site.repo.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public String getRoleNameById(String id) {
        return repository.findById(id).get().getRoleName();
    }
}
