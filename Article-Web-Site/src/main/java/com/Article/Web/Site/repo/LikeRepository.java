package com.Article.Web.Site.repo;

import com.Article.Web.Site.model.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, String> {
    @Modifying
    @Query("""
        update LikeEntity l set l.likeStatus = true where l.id = ?1
    """)
    void updateStatusById(String id);

    @Modifying
    @Query("""
        update LikeEntity l set l.likeStatus = false where l.fkLikerAccountId = ?1
    """)
    void unLikesArticlesById(String likerId);

    @Modifying
    @Query("""
        update LikeEntity l set l.likeStatus = false where l.id = ?1
    """)
    Optional<LikeEntity> unLikeArticleById(String id);
}
