package com.Article.Web.Site.repo;

import com.Article.Web.Site.model.AccountNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountNumberRepository extends JpaRepository<AccountNumberEntity, String> {

}
