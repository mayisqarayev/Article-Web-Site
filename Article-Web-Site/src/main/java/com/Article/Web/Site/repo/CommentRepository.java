package com.Article.Web.Site.repo;

import com.Article.Web.Site.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, String> {

    @Modifying
    @Query("""
        update CommentEntity c set c.commentStatus = true where c.id = ?1
    """)
    void updateStatusById(String id);

    @Modifying
    @Query("update CommentEntity c set c.commentStatus = false where c.id = ?1")
    Optional<CommentEntity> deleteCommentById(String id);
}
