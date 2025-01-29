package com.Article.Web.Site.dto;

import com.Article.Web.Site.model.ImageEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ArticleResponseDto {
    private String articleHeader;
    private String articleText;
    private LocalDate articleDeploymentDate;
    private BigDecimal countOfReaders;
    private BigDecimal countOfLikes;
    private BigDecimal countOfComments;
    private CategoryResponseDto category;
    private AccountResponseDto account;
    private List<ImageResponseDto> images;

}
