package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.ImageConverter;
import com.Article.Web.Site.dto.request.AddImageRequestDto;
import com.Article.Web.Site.model.ImageEntity;
import com.Article.Web.Site.repo.ImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageRepository repository;
    private final ImageConverter converter;

    public ImageService(ImageRepository repository, ImageConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public void addImage(AddImageRequestDto requestDto) {
        repository.save(
            ImageEntity.builder()
                .imageUrl(requestDto.getImageUrl())
                .imageStatus(true)
                .fkArticleId(requestDto.getFkArticleId()).build()
        );
    }

    protected ImageEntity transformImageEntity(AddImageRequestDto requestDto) {
        return converter.toImageEntityFromAddImageRequestDto(requestDto);
    }
}
