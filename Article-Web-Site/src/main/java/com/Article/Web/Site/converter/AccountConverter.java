package com.Article.Web.Site.converter;

import com.Article.Web.Site.dto.request.CreateAccountRequestDto;
import com.Article.Web.Site.dto.response.AccountInfoResponseDto;
import com.Article.Web.Site.dto.response.AccountResponseDto;
import com.Article.Web.Site.model.AccountEntity;
import com.Article.Web.Site.service.AccountNumberService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class AccountConverter {

    private final AccountNumberService numberService;

    public AccountConverter(AccountNumberService numberService) {
        this.numberService = numberService;
    }

    public AccountInfoResponseDto toAccountInfoResponseDtoFromEntity(AccountEntity entity) {
        return AccountInfoResponseDto.builder()
                .accountName(entity.getAccountName())
                .accountDescription(entity.getAccountDescription())
                .accountEmail(entity.getAccountEmail())
                .accountCreationDate(entity.getAccountCreationDate())
                .countOfFollowers(entity.getCountOfFollowers())
                .accountNumber(entity.getAccountNumber())
                .accountProfilePhotoUrl(entity.getAccountProfilePhotoUrl()).build();
    }

    public AccountResponseDto toAccountResponseDtoFromEntity(AccountEntity entity) {
        return AccountResponseDto.builder()
                .accountName(entity.getAccountName())
                .countOfFollowers(entity.getCountOfFollowers())
                .photoUrl(entity.getAccountProfilePhotoUrl()).build();
    }

    public AccountEntity toEntityFromCreateAccountRequestDto(CreateAccountRequestDto requestDto) {
        return AccountEntity.builder()
                .accountName(requestDto.getAccountName())
                .accountDescription("Description Not Setted")
                .accountEmail(requestDto.getAccountEmail())
                .accountCreationDate(LocalDate.now())
                .accountStatus(true)
                .countOfFollowers(BigDecimal.ZERO)
                .accountNumber(numberService.generateAccountNumber())
                .accountProfilePhotoUrl(requestDto.getAccountProfilePhotoUrl())
                .fkUserId(requestDto.getFkUserId()).build();
    }

}
