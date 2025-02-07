package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.ImageConverter;
import com.Article.Web.Site.dto.request.AddImageRequestDto;
import com.Article.Web.Site.dto.response.ImageResponseDto;
import com.Article.Web.Site.exception.EmptyDataException;
import com.Article.Web.Site.exception.InvalidArgumentException;
import com.Article.Web.Site.model.ImageEntity;
import com.Article.Web.Site.repo.ImageRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final ImageRepository repository;
    private final ImageConverter converter;

    public ImageService(ImageRepository repository, ImageConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public void addImage(AddImageRequestDto requestDto) {
        Optional.ofNullable(requestDto).ifPresentOrElse(
                action -> {
                    repository.save(
                            ImageEntity.builder()
                                    .imageUrl(requestDto.getImageUrl())
                                    .imageStatus(true)
                                    .fkArticleId(requestDto.getFkArticleId()).build()
                    );
                },
                () -> {
                    throw new InvalidArgumentException("Request is null");
                }
        );
    }

    @Cacheable(value = "images", key = "#articleId")
    public List<ImageResponseDto> getImagesByArticleId(String articleId) {
        Optional.ofNullable(articleId).orElseThrow(() -> new InvalidArgumentException("ArticleId is null"));
        List<ImageEntity> images = repository.findAll();
        if(images.isEmpty()) throw new EmptyDataException("Images is empty");

        return images.stream()
                .filter(imageEntity -> imageEntity.getFkArticleId().equals(articleId))
                .map(imageEntity -> new ImageResponseDto(imageEntity.getImageUrl()))
                .collect(Collectors.toList());
    }

    protected ImageEntity transformImageEntity(AddImageRequestDto requestDto) {
        Optional.ofNullable(requestDto).orElseThrow(() -> new InvalidArgumentException("ArticleId is null"));
        return converter.toImageEntityFromAddImageRequestDto(requestDto);
    }
}