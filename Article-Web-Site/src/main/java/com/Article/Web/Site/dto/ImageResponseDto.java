package com.Article.Web.Site.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageResponseDto {

    private String imageUrl;

    public ImageResponseDto() {
    }

    public ImageResponseDto(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
