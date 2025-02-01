package com.Article.Web.Site.converter;

import com.Article.Web.Site.dto.request.SendCommentRequestDto;
import com.Article.Web.Site.dto.response.CommentResponseDto;
import com.Article.Web.Site.model.CommentEntity;
import com.Article.Web.Site.service.AccountService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class CommentConverter {

    private final AccountService accountService;

    public CommentConverter(AccountService accountService) {
        this.accountService = accountService;
    }

    public CommentEntity toEntityFromSendCommentRequestDto(SendCommentRequestDto requestDto) {
        return CommentEntity.builder()
                .commentText(requestDto.getCommentText())
                .commentStatus(true)
                .deployDate(LocalDateTime.now())
                .fkSenderAccountId(requestDto.getFkSenderAccountId())
                .fkReceiverArticleId(requestDto.getFkReceiverArticleId()).build();
    }

    public CommentResponseDto toCommentResponseDtoFromEntity(CommentEntity entity) {
        return CommentResponseDto.builder()
                .commentText(entity.getCommentText())
                .deployDate(entity.getDeployDate())
                .account(accountService.getAccountById(entity.getFkSenderAccountId())).build();
    }

}
