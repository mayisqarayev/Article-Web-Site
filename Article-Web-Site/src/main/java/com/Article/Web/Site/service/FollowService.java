package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.FollowConverter;
import com.Article.Web.Site.dto.request.FollowRequestDto;
import com.Article.Web.Site.model.FollowEntity;
import com.Article.Web.Site.repo.FollowRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FollowService {

    private final FollowRepository repository;
    private final FollowConverter converter;
    private final AccountService accountService;

    public FollowService(FollowRepository repository, FollowConverter converter, AccountService accountService) {
        this.repository = repository;
        this.converter = converter;
        this.accountService = accountService;
    }

    public void followAccount(FollowRequestDto requestDto) {
        List<FollowEntity> follows = repository.findAll();
        follows.forEach(follow -> {
            FollowRequestDto dto = FollowRequestDto.builder()
                    .fkFollowerAccountId(follow.getFkFollowerAccountId())
                    .fkFollowedAccountId(follow.getFkFollowedAccountId()).build();

            if(dto.equals(requestDto) && follow.getFollowStatus().equals(false)) {
                repository.updateStatusById(follow.getId());
                return;
            }
        });

        repository.save(converter.toEntityFromFollowRequestDto(requestDto));
        accountService.updateAccountFollowerCountById(requestDto.getFkFollowedAccountId());
    }

    public void unFollow(String id) {
        repository.unFollowAccountById(id);
    }

    protected List<FollowEntity> getFollows() {
        return repository.findAll();
    }
}
