package com.Article.Web.Site.repo;

import com.Article.Web.Site.model.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, String> {

}
