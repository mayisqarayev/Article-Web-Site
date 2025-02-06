package com.Article.Web.Site.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddTagRequestDto {

    private String tagName;
    private String articleId;

    public AddTagRequestDto() {
    }

    public AddTagRequestDto(String tagName, String articleId) {
        this.tagName = tagName;
        this.articleId = articleId;
    }
}
