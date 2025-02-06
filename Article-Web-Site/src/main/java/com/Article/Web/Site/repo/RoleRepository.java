package com.Article.Web.Site.repo;

import com.Article.Web.Site.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {

    @Transactional
    @Modifying
    @Query("""
        update RoleEntity r set r.roleStatus = false where r.id = ?1
    """)
    void deleteRoleById(String id);
}
