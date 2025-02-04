package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.AccountConverter;
import com.Article.Web.Site.dto.request.AccountNumberRequestDto;
import com.Article.Web.Site.dto.request.AccountRequestDto;
import com.Article.Web.Site.dto.request.CreateAccountRequestDto;
import com.Article.Web.Site.dto.response.AccountResponseDto;
import com.Article.Web.Site.exception.InvalidArgumentException;
import com.Article.Web.Site.model.AccountEntity;
import com.Article.Web.Site.model.FollowEntity;
import com.Article.Web.Site.model.UserEntity;
import com.Article.Web.Site.repo.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository repository;
    private final AccountConverter converter;
    private final FollowService followService;
    private final AccountNumberService numberService;
    private final UserService userService;

    public AccountService(AccountRepository repository, AccountConverter converter, FollowService followService, AccountNumberService numberService, UserService userService) {
        this.repository = repository;
        this.converter = converter;
        this.followService = followService;
        this.numberService = numberService;
        this.userService = userService;
    }


    public AccountResponseDto getAccountById(String id) {
        return converter.toAccountResponseDtoFromEntity(repository.findById(id).get());
    }

    protected void updateAccountFollowerCountById(String id) {
        repository.updateCountOfFollowersById(id);
    }

    public List<AccountResponseDto> getFollowerAccountsById(String id) {
        List<FollowEntity> follows = followService.getFollows();
        List<String> followerIds = follows.stream()
                .filter(follow -> follow.getFkFollowedAccountId().equals(id))
                .sorted(Comparator.comparing(FollowEntity::getFollowDate).reversed())
                .map(FollowEntity::getFkFollowerAccountId)
                .distinct().toList();

        return repository.findAll().stream()
                .filter(entity -> followerIds.contains(entity.getId()))
                .map(converter::toAccountResponseDtoFromEntity)
                .collect(Collectors.toList());
    }

    public List<AccountResponseDto> getFollowedAccountsById(String id) {
        List<FollowEntity> follows = followService.getFollows();
        List<String> followedIds = follows.stream()
                .filter(follow -> follow.getFkFollowerAccountId().equals(id))
                .sorted(Comparator.comparing(FollowEntity::getFollowDate).reversed())
                .map(FollowEntity::getFkFollowedAccountId)
                .distinct().toList();

        return repository.findAll().stream()
                .filter(entity -> followedIds.contains(entity.getId()))
                .map(converter::toAccountResponseDtoFromEntity)
                .collect(Collectors.toList());
    }

    public void createAccount(CreateAccountRequestDto requestDto) {

        userService.validateUserById(requestDto.getFkUserId());

        AccountEntity entity = repository.save(converter.toEntityFromCreateAccountRequestDto(requestDto));
        numberService.saveNumber(
                AccountNumberRequestDto.builder()
                        .number(entity.getAccountNumber())
                        .accountId(entity.getId())
                        .build()
        );
    }
}
