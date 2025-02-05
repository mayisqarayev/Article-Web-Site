package com.Article.Web.Site.service;

import com.Article.Web.Site.repo.SaveRepository;
import org.springframework.stereotype.Service;

@Service
public class SaveService {

    private final SaveRepository repository;

    public SaveService(SaveRepository repository) {
        this.repository = repository;
    }

    public void deleteSavesBySaverId(String saverId) {
        repository.deleteSavesById(saverId);
    }
}
