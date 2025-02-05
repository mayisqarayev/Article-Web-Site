package com.Article.Web.Site.repo;

import com.Article.Web.Site.model.AccountEntity;
import com.Article.Web.Site.model.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, String> {

    @Modifying
    @Query("""
        update FollowEntity f set f.followStatus = true where f.id = ?1
    """)
    void updateStatusById(String id);

    @Modifying
    @Query("""
        update FollowEntity f set f.followStatus = false where f.fkFollowerAccountId = ?1
    """)
    void unFollowsAccountsById(String followerId);

    @Modifying
    @Query("""
        update FollowEntity f set f.followStatus = false where f.id = ?1
    """)
    Optional<FollowEntity> unFollowAccountById(String id);
}
