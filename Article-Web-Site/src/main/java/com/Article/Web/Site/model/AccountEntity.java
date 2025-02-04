package com.Article.Web.Site.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Builder
@Data
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String accountName;
    private String accountDescription;
    private String accountEmail;
    private LocalDate accountCreationDate;
    private Boolean accountStatus;
    private BigDecimal countOfFollowers;
    private String accountNumber;
    private String accountProfilePhotoUrl;
    private String fkUserId;

    public AccountEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(accountName, that.accountName) && Objects.equals(accountDescription, that.accountDescription) && Objects.equals(accountEmail, that.accountEmail) && Objects.equals(accountCreationDate, that.accountCreationDate) && Objects.equals(accountStatus, that.accountStatus) && Objects.equals(countOfFollowers, that.countOfFollowers) && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(accountProfilePhotoUrl, that.accountProfilePhotoUrl) && Objects.equals(fkUserId, that.fkUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountName, accountDescription, accountEmail, accountCreationDate, accountStatus, countOfFollowers, accountNumber, accountProfilePhotoUrl, fkUserId);
    }

    public AccountEntity(String id, String accountName, String accountDescription, String accountEmail, LocalDate accountCreationDate, Boolean accountStatus, BigDecimal countOfFollowers, String accountNumber, String accountProfilePhotoUrl, String fkUserId) {
        this.id = id;
        this.accountName = accountName;
        this.accountDescription = accountDescription;
        this.accountEmail = accountEmail;
        this.accountCreationDate = accountCreationDate;
        this.accountStatus = accountStatus;
        this.countOfFollowers = countOfFollowers;
        this.accountNumber = accountNumber;
        this.accountProfilePhotoUrl = accountProfilePhotoUrl;
        this.fkUserId = fkUserId;
    }
}
