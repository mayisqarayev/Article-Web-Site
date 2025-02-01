package com.Article.Web.Site.repo;

import com.Article.Web.Site.model.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, String> {
    @Modifying
    @Query("""
        update LikeEntity l set l.likeStatus = false where l.id = ?1
    """)
    void updateStatusById(String id);

    @Modifying
    @Query("""
        update LikeEntity l set l.likeStatus = false where l.id = ?1
    """)
    void unLikeArticleById(String id);
}
