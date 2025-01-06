package com.Article.Web.Site.repo;

import com.Article.Web.Site.model.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, String> {
}
