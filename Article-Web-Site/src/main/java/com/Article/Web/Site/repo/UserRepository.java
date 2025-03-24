package com.Article.Web.Site.repo;

import com.Article.Web.Site.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Transactional
    @Modifying
    @Query("""
        update UserEntity u set u.isEnabled = false where u.id = ?1
    """)
    void deleteUserById(String id);

    Optional<UserEntity> findUserEntityByUsername(String username);
}
