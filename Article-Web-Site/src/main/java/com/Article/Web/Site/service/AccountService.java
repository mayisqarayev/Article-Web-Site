package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.AccountConverter;
import com.Article.Web.Site.dto.request.AccountNumberRequestDto;
import com.Article.Web.Site.dto.request.CreateAccountRequestDto;
import com.Article.Web.Site.dto.request.TransferAccountRequestDto;
import com.Article.Web.Site.dto.request.UpdateAccountRequestDto;
import com.Article.Web.Site.dto.response.AccountResponseDto;
import com.Article.Web.Site.dto.response.AccountInfoResponseDto;
import com.Article.Web.Site.exception.AccountNotFoundException;
import com.Article.Web.Site.exception.EmptyDataException;
import com.Article.Web.Site.exception.InvalidArgumentException;
import com.Article.Web.Site.exception.InvalidStateException;
import com.Article.Web.Site.model.AccountEntity;
import com.Article.Web.Site.model.FollowEntity;
import com.Article.Web.Site.repo.AccountRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository repository;
    private final AccountConverter converter;
    private final AccountNumberService numberService;
    private final UserService userService;
    private final ArticleService articleService;
    private final CommentService commentService;
    private final LikeService likeService;
    private final FollowService followService;
    private final SaveService saveService;

    public AccountService(AccountRepository repository, AccountConverter converter, FollowService followService, AccountNumberService numberService, UserService userService, ArticleService articleService, CommentService commentService, LikeService likeService, SaveService saveService) {
        this.repository = repository;
        this.converter = converter;
        this.followService = followService;
        this.numberService = numberService;
        this.userService = userService;
        this.articleService = articleService;
        this.commentService = commentService;
        this.likeService = likeService;
        this.saveService = saveService;
    }

    public AccountResponseDto getAccountById(String id) {
        return converter.toAccountResponseDtoFromEntity(repository.findById(id).get());
    }

    public AccountInfoResponseDto getAccountInfoById(String id) {
        Optional.ofNullable(id).orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
        return converter.toAccountInfoResponseDtoFromEntity(repository.findById(id).get());
    }

    public List<AccountResponseDto> getAccountsByUserId(String userId) {
        return repository.findAll().stream()
                .filter(entity -> entity.getFkUserId().equals(userId))
                .sorted(Comparator.comparing(AccountEntity::getAccountCreationDate).reversed())
                .map(converter::toAccountResponseDtoFromEntity)
                .collect(Collectors.toList());
    }

    public List<AccountResponseDto> getMostPopularAccounts() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(AccountEntity::getCountOfFollowers).reversed())
                .map(converter::toAccountResponseDtoFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void transferAccount(TransferAccountRequestDto requestDto) {
        Optional.ofNullable(requestDto)
                .orElseThrow(() -> new InvalidArgumentException("Request is null"));

        AccountEntity account = repository.findById(requestDto.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account Not Found"));

        if(!account.getFkUserId().equals(requestDto.getCurrentUserId()))
            throw new InvalidStateException("You are not authorized to transfer this account!");

        account.setFkUserId(requestDto.getNewUserId());
        repository.save(account);
        //RabbitMQ
    }

    @Cacheable(value = "popularityScore", key = "#id")
    public Double calculatePopularityScore(String id) {

        Optional.ofNullable(id)
                .orElseThrow(() -> new InvalidArgumentException("Id is null"));

        AccountEntity account = repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
        double accountAgeDays = LocalDate.now().toEpochDay() - account.getAccountCreationDate().toEpochDay();

        return (account.getCountOfFollowers().doubleValue() * 10)/ (accountAgeDays + 1);
    }

    public void deleteAccountById(String id) {
        Optional.ofNullable(id).ifPresentOrElse(
                action -> {
                    repository.deleteAccountById(id);
                    articleService.deleteArticlesByAccountId(id);
                    followService.deleteFollowsByFollowerId(id);
                    likeService.deleteLikesByLikerId(id);
                    commentService.deleteCommentsBySenderId(id);
                    saveService.deleteSavesBySaverId(id);
                },
                () -> {
                    throw new InvalidArgumentException("Id is null");
                });
    }

    @Async
    public CompletableFuture<Void> batchDeleteAccountsByIds(List<String> ids) {
        Optional.ofNullable(ids)
                .orElseThrow(() -> new InvalidArgumentException("Request is null"));

        if(ids.isEmpty()) throw new EmptyDataException("Ids is empty");

        List<AccountEntity> willBeDeletedAccounts = new ArrayList<>();

        ids.forEach(id -> {
                willBeDeletedAccounts.add(repository.findById(id)
                        .orElseThrow(() -> new AccountNotFoundException("Account Not Found")));
        });

        if(!willBeDeletedAccounts.isEmpty())
            willBeDeletedAccounts.forEach(account -> {
                account.setAccountStatus(false);
                articleService.deleteArticlesByAccountId(account.getId());
                followService.deleteFollowsByFollowerId(account.getId());
                likeService.deleteLikesByLikerId(account.getId());
                commentService.deleteCommentsBySenderId(account.getId());
                saveService.deleteSavesBySaverId(account.getId());
            });

        repository.saveAll(willBeDeletedAccounts);
        return CompletableFuture.completedFuture(null);
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

    public void updateAccount(UpdateAccountRequestDto requestDto) {
        Optional.ofNullable(requestDto).ifPresentOrElse(
                action -> {
                    repository.updateAccountById(
                            requestDto.getId(),
                            requestDto.getAccountName(),
                            requestDto.getAccountDescription(),
                            requestDto.getAccountEmail(),
                            requestDto.getAccountProfilePhotoUrl());
                },
                () -> {
                    throw new AccountNotFoundException("Account Not Found");
                }
        );
    }

    protected void increaseAccountFollowerCountById(String id) {
        repository.increaseCountOfFollowersById(id);
    }

    protected void decreaseAccountFollowerCountById(String id) {
        repository.decreaseAccountFollowerCountById(id);
    }
}
