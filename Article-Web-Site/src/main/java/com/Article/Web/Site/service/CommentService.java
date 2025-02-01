package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.CommentConverter;
import com.Article.Web.Site.dto.request.SendCommentRequestDto;
import com.Article.Web.Site.dto.response.CommentResponseDto;
import com.Article.Web.Site.model.CommentEntity;
import com.Article.Web.Site.repo.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
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

        List<CommentEntity> comments = repository.findAll();
        comments.forEach(comment -> {
            SendCommentRequestDto dto = SendCommentRequestDto.builder()
                    .commentText(comment.getCommentText())
                    .fkSenderAccountId(comment.getFkSenderAccountId())
                    .fkReceiverArticleId(comment.getFkReceiverArticleId())
                    .build();

            if(requestDto.equals(dto) && comment.getCommentStatus().equals(false)) {
                repository.updateStatusById(comment.getId());
                return;
            }
        });

        repository.save(converter.toEntityFromSendCommentRequestDto(requestDto));
        articleService.updateArticleCommentCountById(requestDto.getFkReceiverArticleId());
    }

    public void deleteCommentById(String id) {
        repository.deleteCommentById(id);
    }

    public List<CommentResponseDto> getCommentsByArticleId(String articleId) {
        return repository.findAll().stream()
                .filter(commentEntity -> commentEntity.getFkReceiverArticleId().equals(articleId))
                .map(converter::toCommentResponseDtoFromEntity)
                .sorted(Comparator.comparing(CommentResponseDto::getDeployDate).reversed())
                .collect(Collectors.toList());
    }

    public List<CommentResponseDto> getCommentsByAccountId(String accountId) {
        return repository.findAll().stream()
                .filter(commentEntity -> commentEntity.getFkSenderAccountId().equals(accountId))
                .map(converter::toCommentResponseDtoFromEntity)
                .sorted(Comparator.comparing(CommentResponseDto::getDeployDate).reversed())
                .collect(Collectors.toList());
    }
}
