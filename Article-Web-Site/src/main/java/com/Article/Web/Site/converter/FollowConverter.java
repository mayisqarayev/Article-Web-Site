package com.Article.Web.Site.converter;

import com.Article.Web.Site.dto.request.FollowRequestDto;
import com.Article.Web.Site.model.FollowEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FollowConverter {

    public FollowEntity toEntityFromFollowRequestDto(FollowRequestDto requestDto) {
        return FollowEntity.builder()
                .fkFollowerAccountId(requestDto.getFkFollowerAccountId())
                .fkFollowedAccountId(requestDto.getFkFollowedAccountId())
                .followDate(LocalDateTime.now())
                .followStatus(true).build();
    }

}
