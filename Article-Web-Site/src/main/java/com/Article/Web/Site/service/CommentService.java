package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.CommentConverter;
import com.Article.Web.Site.dto.request.SendCommentRequestDto;
import com.Article.Web.Site.dto.response.CommentResponseDto;
import com.Article.Web.Site.exception.CommentNotFoundException;
import com.Article.Web.Site.exception.EmptyDataException;
import com.Article.Web.Site.exception.InvalidArgumentException;
import com.Article.Web.Site.model.CommentEntity;
import com.Article.Web.Site.repo.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository repository;
    private final CommentConverter converter;
    private final ArticleService articleService;

    public CommentService(CommentRepository repository, CommentConverter converter, ArticleService articleService) {
        this.repository = repository;
        this.converter = converter;
        this.articleService = articleService;
    }

    public void sendComment(SendCommentRequestDto requestDto) {
        Optional.ofNullable(requestDto).orElseThrow(() -> new InvalidArgumentException("Request is null"));

        List<CommentEntity> comments = repository.findAll();
        if (comments.isEmpty()) throw new EmptyDataException("Comments is empty");

        comments.forEach(comment -> {
            SendCommentRequestDto dto = SendCommentRequestDto.builder()
                    .commentText(comment.getCommentText())
                    .fkSenderAccountId(comment.getFkSenderAccountId())
                    .fkReceiverArticleId(comment.getFkReceiverArticleId())
                    .build();

            if (requestDto.equals(dto) && comment.getCommentStatus().equals(false)) {
                repository.updateStatusById(comment.getId());
                return;
            }
        });

        repository.save(converter.toEntityFromSendCommentRequestDto(requestDto));
        articleService.increaseArticleCommentCountById(requestDto.getFkReceiverArticleId());
    }

    public void deleteComment(String id) {
        Optional.ofNullable(id).ifPresentOrElse(
                action -> {
                    CommentEntity commentEntity = repository.deleteCommentById(id)
                            .orElseThrow(() -> new CommentNotFoundException("Comment Not Found"));
                    articleService.decreaseArticleCommentCountById(commentEntity.getFkReceiverArticleId());
                },
                () -> {
                    throw new InvalidArgumentException("Id is null");
                }
        );
    }

    public void deleteCommentsBySenderId(String senderId) {
        Optional.ofNullable(senderId).ifPresentOrElse(
                action -> {
                    repository.deleteCommentsById(senderId);
                },
                () -> {
                    throw new InvalidArgumentException("SenderId is null");
                }
        );
    }

    public List<CommentResponseDto> getCommentsByArticleId(String articleId) {
        Optional.ofNullable(articleId).orElseThrow(() -> new InvalidArgumentException("ArticleId is null"));
        List<CommentEntity> comments = repository.findAll();
        if(comments.isEmpty()) throw new EmptyDataException("Comments is empty");

        return comments.stream()
                .filter(commentEntity -> commentEntity.getFkReceiverArticleId().equals(articleId))
                .map(converter::toCommentResponseDtoFromEntity)
                .sorted(Comparator.comparing(CommentResponseDto::getDeployDate).reversed())
                .collect(Collectors.toList());
    }

    public List<CommentResponseDto> getCommentsByAccountId(String accountId) {
        Optional.ofNullable(accountId).orElseThrow(() -> new InvalidArgumentException("AccountId is null"));
        List<CommentEntity> comments = repository.findAll();
        if(comments.isEmpty()) throw new EmptyDataException("Comments is empty");

        return comments.stream()
                .filter(commentEntity -> commentEntity.getFkSenderAccountId().equals(accountId))
                .map(converter::toCommentResponseDtoFromEntity)
                .sorted(Comparator.comparing(CommentResponseDto::getDeployDate).reversed())
                .collect(Collectors.toList());
    }
}