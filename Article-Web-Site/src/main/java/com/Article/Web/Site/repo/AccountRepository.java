package com.Article.Web.Site.repo;

import com.Article.Web.Site.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    @Modifying
    @Query("""
        update AccountEntity a set a.countOfFollowers = a.countOfFollowers + 1 where a.id = ?1
    """)
    void updateCountOfFollowersById(String id);
}
