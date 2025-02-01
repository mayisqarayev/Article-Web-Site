package com.Article.Web.Site.repo;

import com.Article.Web.Site.model.ArticleEntity;
import com.Article.Web.Site.model.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, String> {


    @Modifying
    @Query("""
        update ArticleEntity a set a.countOfLikes = a.countOfLikes + 1 where a.id = ?1
    """)
    void updateCountOfLikesById(String id);

    @Modifying
    @Query("""
        update ArticleEntity a set a.countOfComments = a.countOfComments + 1 where a.id = ?1
    """)
    void updateCountOfCommentsById(String id);

    @Modifying
    @Query("""
        update ArticleEntity a set a.images = ?2 where a.id = ?1
    """)
    void updateArticleEntityImagesById(String id, List<ImageEntity> images);

    @Modifying
    @Query("""
        update ArticleEntity a set a.articleStatus = false where a.id = ?1
    """)
    void deleteArticleEntityById(String id);

    @Query("""
        select a from ArticleEntity as a
        where a.countOfReaders = (select max(a.countOfReaders) from ArticleEntity a)
    """)
    Optional<ArticleEntity> findMostReadedArticleEntity();

    @Query("""
        select a from ArticleEntity as a
        where a.countOfLikes = (select max(a.countOfLikes) from ArticleEntity a)
    """)
    Optional<ArticleEntity> findMostLikedArticleEntity();
}
