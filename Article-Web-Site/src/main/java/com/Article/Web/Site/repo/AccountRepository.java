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
    void increaseCountOfFollowersById(String id);

    @Modifying
    @Query("""
         update AccountEntity a set a.countOfFollowers = a.countOfFollowers - 1 where a.id = ?1
    """)
    void decreaseAccountFollowerCountById(String id);
    @Modifying
    @Query("""
        update AccountEntity a set a.accountStatus = false where a.id = ?1
    """)
    void deleteAccountById(String id);

    @Modifying
    @Query("""
        update AccountEntity a set
        a.accountName = ?2,  
        a.accountDescription = ?3,
        a.accountEmail = ?4,
        a.accountProfilePhotoUrl = ?5 where a.id = ?1
    """)
    void updateAccountById(
            String id,
            String accountName,
            String accountDescription,
            String accountEmail,
            String accountProfilePhotoUrl
    );

}
