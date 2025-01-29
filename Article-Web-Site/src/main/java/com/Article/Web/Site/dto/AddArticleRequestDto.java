package com.Article.Web.Site.dto;

import com.Article.Web.Site.model.ImageEntity;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class AddArticleRequestDto {

    private String articleHeader;
    private String articleText;
    private String fkCategoryId;
    private String fkAccountId;
    private List<AddImageRequestDto> addedImages;

    public AddArticleRequestDto() {
    }

    public AddArticleRequestDto(String articleHeader, String articleText, String fkCategoryId, String fkAccountId, List<AddImageRequestDto> addedImages) {
        this.articleHeader = articleHeader;
        this.articleText = articleText;
        this.fkCategoryId = fkCategoryId;
        this.fkAccountId = fkAccountId;
        this.addedImages = addedImages;
    }
}
