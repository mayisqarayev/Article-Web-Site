package com.Article.Web.Site.repo;

import com.Article.Web.Site.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {

    @Modifying
    @Query("update CategoryEntity c set c.categoryStatus = true where c.id = ?1")
    void deleteCategoryById(String id);

}
