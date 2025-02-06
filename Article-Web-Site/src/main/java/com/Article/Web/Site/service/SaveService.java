package com.Article.Web.Site.service;

import com.Article.Web.Site.converter.SaveConverter;
import com.Article.Web.Site.dto.request.LikeRequestDto;
import com.Article.Web.Site.dto.request.SaveRequestDto;
import com.Article.Web.Site.model.LikeEntity;
import com.Article.Web.Site.model.SaveEntity;
import com.Article.Web.Site.repo.SaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveService {

    private final SaveRepository repository;
    private final SaveConverter converter;

    public SaveService(SaveRepository repository, SaveConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public void saveArticle(SaveRequestDto requestDto) {

        List<SaveEntity> saves = repository.findAll();
        saves.forEach(save -> {
            SaveRequestDto dto = SaveRequestDto.builder()
                    .fkSaverAccountId(save.getFkSaverAccountId())
                    .fkSavedArticleId(save.getFkSavedArticleId())
                    .build();

            if(dto.equals(requestDto) && save.getSaveStatus().equals(false)) {
                repository.updateStatusById(save.getId()); return;
            }
        });

        repository.save(converter.toEntityFromSaveRequestDto(requestDto));
    }

    public void unSaveArticle(String id) {
        repository.deleteSaveById(id);
    }

    public void deleteSavesBySaverId(String saverId) {
        repository.deleteSavesById(saverId);
    }
}
