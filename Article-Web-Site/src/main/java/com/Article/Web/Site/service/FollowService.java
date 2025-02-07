package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.FollowConverter;
import com.Article.Web.Site.dto.request.FollowRequestDto;
import com.Article.Web.Site.exception.EmptyDataException;
import com.Article.Web.Site.exception.FollowNotFoundException;
import com.Article.Web.Site.exception.InvalidArgumentException;
import com.Article.Web.Site.model.AccountEntity;
import com.Article.Web.Site.model.FollowEntity;
import com.Article.Web.Site.repo.FollowRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class FollowService {

    private final FollowRepository repository;
    private final FollowConverter converter;
    private final AccountService accountService;

    public FollowService(FollowRepository repository, FollowConverter converter, @Lazy AccountService accountService) {
        this.repository = repository;
        this.converter = converter;
        this.accountService = accountService;
    }

    public void followAccount(FollowRequestDto requestDto) {
        Optional.ofNullable(requestDto).ifPresentOrElse(
                action -> {
                    List<FollowEntity> follows = repository.findAll();
                    if (follows.isEmpty()) throw new EmptyDataException("Follows is empty");

                    follows.forEach(follow -> {
                        FollowRequestDto dto = FollowRequestDto.builder()
                                .fkFollowerAccountId(follow.getFkFollowerAccountId())
                                .fkFollowedAccountId(follow.getFkFollowedAccountId()).build();

                        if (dto.equals(requestDto) && follow.getFollowStatus().equals(false)) {
                            repository.updateStatusById(follow.getId());
                            return;
                        }
                    });

                    repository.save(converter.toEntityFromFollowRequestDto(requestDto));
                    accountService.increaseAccountFollowerCountById(requestDto.getFkFollowedAccountId());
                },
                () -> {
                    throw new InvalidArgumentException("Request is null");
                }
        );
    }

    public void unFollow(String id) {
        Optional.ofNullable(id).ifPresentOrElse(
                action -> {
                    FollowEntity followEntity = repository.unFollowAccountById(id)
                            .orElseThrow(() -> new FollowNotFoundException("Follow Not Found"));
                    accountService.decreaseAccountFollowerCountById(followEntity.getFkFollowedAccountId());
                },
                () -> {
                    throw new InvalidArgumentException("Id is null");
                }
        );
    }

    public void deleteFollowsByFollowerId(String followerId) {
        Optional.ofNullable(followerId).ifPresentOrElse(
                action -> {
                    repository.unFollowsAccountsById(followerId);
                },
                () -> {
                    throw new InvalidArgumentException("FollowerId is null");
                }
        );
    }

    protected List<FollowEntity> getFollows() {
        List<FollowEntity> follows = repository.findAll();
        if (follows.isEmpty()) throw new EmptyDataException("Follows is empty");
        return follows;
    }
}
