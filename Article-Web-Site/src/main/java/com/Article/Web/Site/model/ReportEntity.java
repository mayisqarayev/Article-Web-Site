package com.Article.Web.Site.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Entity
@Builder
@Data
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Boolean reportStatus;

    private String fkReporterAccountId;
    private String fkReportedArticleId;

    public ReportEntity() {
    }

    public ReportEntity(String id, Boolean reportStatus, String fkReporterAccountId, String fkReportedArticleId) {
        this.id = id;
        this.reportStatus = reportStatus;
        this.fkReporterAccountId = fkReporterAccountId;
        this.fkReportedArticleId = fkReportedArticleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(reportStatus, that.reportStatus) && Objects.equals(fkReporterAccountId, that.fkReporterAccountId) && Objects.equals(fkReportedArticleId, that.fkReportedArticleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reportStatus, fkReporterAccountId, fkReportedArticleId);
    }
}
