package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.UserConverter;
import com.Article.Web.Site.dto.request.CreateUserRequestDto;
import com.Article.Web.Site.dto.response.UserInfoResponseDto;
import com.Article.Web.Site.dto.response.UserResponseDto;
import com.Article.Web.Site.exception.EmptyDataException;
import com.Article.Web.Site.exception.InvalidArgumentException;
import com.Article.Web.Site.exception.UserNotFoundException;
import com.Article.Web.Site.model.AccountEntity;
import com.Article.Web.Site.model.UserEntity;
import com.Article.Web.Site.repo.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserConverter converter;
    private final AccountService accountService;

    public UserService(UserRepository repository, UserConverter converter, @Lazy AccountService accountService) {
        this.repository = repository;
        this.converter = converter;
        this.accountService = accountService;
    }

    public UserResponseDto getUserById(String id) {
        Optional.ofNullable(id).orElseThrow(() -> new InvalidArgumentException("Id is null"));

        return converter.toUserResponseDtoFromEntity(repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found")));
    }

    public UserInfoResponseDto getUserInfoById(String id) {
        Optional.ofNullable(id).orElseThrow(() -> new InvalidArgumentException("Id is null"));

        return converter.toUserInfoResponseDtoFromEntity(repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found")));
    }

    public List<UserResponseDto> getUsers() {
        List<UserEntity> users = repository.findAll();

        return users.stream()
                .map(converter::toUserResponseDtoFromEntity)
                .collect(Collectors.toList());
    }

    public void createUser(CreateUserRequestDto requestDto) {
        Optional.ofNullable(requestDto).ifPresentOrElse(
                action -> {
                    repository.save(converter.toEntityFromCreateUserRequestDto(requestDto));
                },
                () -> {
                    throw new InvalidArgumentException("Request is null");
                }
        );
    }

    public void deleteUserById(String id) {
        Optional.ofNullable(id).ifPresentOrElse(
                action -> {
                    repository.deleteUserById(id);
                    UserEntity user = repository.findById(id)
                            .orElseThrow(() -> new UserNotFoundException("User Not Found"));

                    List<AccountEntity> accounts = user.getAccounts();
                    if (accounts.isEmpty()) throw new EmptyDataException("Accounts is empty");

                    accounts.forEach(account -> {
                        accountService.deleteAccountById(account.getId());
                    });
                },
                () -> {
                    throw new InvalidArgumentException("Id is null");
                }
        );
    }

    @Async
    public CompletableFuture<Void> batchDeleteUsersByIds(List<String> ids) {
        Optional.ofNullable(ids).orElseThrow(() -> new InvalidArgumentException("Ids is null"));
        if (ids.isEmpty()) throw new EmptyDataException("Ids is empty");

        List<UserEntity> willBeDeletedUsers = new ArrayList<>();
        ids.forEach(id -> {
            willBeDeletedUsers.add(repository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User Not Found")));
        });

        if (!willBeDeletedUsers.isEmpty())
            willBeDeletedUsers.forEach(user -> {
                user.setEnabled(false);
                accountService.deleteAccountById(user.getId());
            });

        repository.saveAll(willBeDeletedUsers);
        return CompletableFuture.completedFuture(null);
    }

    protected void checkUserById(String id) {
        Optional.ofNullable(id).orElseThrow(() -> new InvalidArgumentException("Id is null"));
        repository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }
}