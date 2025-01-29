package com.Article.Web.Site.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Entity
@Builder
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {
        "report_status", "fk_reporter_account_id", "fk_reported_article_id"
})})
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "report_status")
    private Boolean reportStatus;

    @Column(name = "fk_reporter_account_id")
    private String fkReporterAccountId;

    @Column(name = "fk_reported_article_id")
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
