package com.Article.Web.Site.repo;

import com.Article.Web.Site.model.SaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveRepository extends JpaRepository<SaveEntity, String> {
}
