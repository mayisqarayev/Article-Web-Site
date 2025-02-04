package com.Article.Web.Site.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Entity
@Builder
@Data
public class AccountNumberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String numberOfAccount;
    private String fkAccountId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountNumberEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(numberOfAccount, that.numberOfAccount) && Objects.equals(fkAccountId, that.fkAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfAccount, fkAccountId);
    }

    public AccountNumberEntity() {
    }

    public AccountNumberEntity(String id, String numberOfAccount, String fkAccountId) {
        this.id = id;
        this.numberOfAccount = numberOfAccount;
        this.fkAccountId = fkAccountId;
    }
}
