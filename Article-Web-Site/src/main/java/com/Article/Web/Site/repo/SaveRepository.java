package com.Article.Web.Site.repo;

import com.Article.Web.Site.model.SaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SaveRepository extends JpaRepository<SaveEntity, String> {

    @Transactional
    @Modifying
    @Query("""
        update SaveEntity s set s.saveStatus = false where s.fkSaverAccountId = ?1
    """)
    void deleteSavesById(String saverId);

    @Transactional
    @Modifying
    @Query("""
        update SaveEntity s set s.saveStatus = true where s.id = ?1
    """)
    void updateStatusById(String id);

    @Transactional
    @Modifying
    @Query("""
        update SaveEntity s set s.saveStatus = false where s.id = ?1
    """)
    void deleteSaveById(String id);
}
