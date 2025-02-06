package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.TagConverter;
import com.Article.Web.Site.dto.request.AddTagRequestDto;
import com.Article.Web.Site.exception.InvalidArgumentException;
import com.Article.Web.Site.repo.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository repository;
    private final TagConverter converter;

    public TagService(TagRepository repository, TagConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    protected void addTags(List<AddTagRequestDto> addedTags) {
        Optional.ofNullable(addedTags).ifPresentOrElse(
                action -> {
                    repository.saveAll(addedTags.stream()
                            .map(converter::toEntityFromAddTagRequestDto)
                            .collect(Collectors.toList()));
                },
                () -> {
                    throw new InvalidArgumentException("AddedTags is null");
                }
        );
    }
}
