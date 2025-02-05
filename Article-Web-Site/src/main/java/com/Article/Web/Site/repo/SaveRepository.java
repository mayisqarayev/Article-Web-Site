package com.Article.Web.Site.repo;

import com.Article.Web.Site.model.SaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveRepository extends JpaRepository<SaveEntity, String> {

    @Modifying
    @Query("""
        update SaveEntity s set s.saveStatus = false where s.fkSaverAccountId = ?1
    """)
    void deleteSavesById(String saverId);

}
