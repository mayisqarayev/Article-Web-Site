package com.Article.Web.Site.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDto {

    private String commentText;
    private AccountResponseDto account;
    private LocalDateTime deployDate;

    public CommentResponseDto() {
    }

    public CommentResponseDto(String commentText, AccountResponseDto account, LocalDateTime deployDate) {
        this.commentText = commentText;
        this.account = account;
        this.deployDate = deployDate;
    }
}
