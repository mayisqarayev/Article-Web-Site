package com.Article.Web.Site.repo;

import com.Article.Web.Site.model.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, String> {

    @Query("""
        select count(f) from FollowEntity f 
        where f.fkFollowedAccountId = ?1 
        group by f.fkFollowedAccountId
    """)
    BigDecimal calculateTotalFollowersById(String id);
}
