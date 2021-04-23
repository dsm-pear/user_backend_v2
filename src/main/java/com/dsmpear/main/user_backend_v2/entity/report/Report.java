package com.dsmpear.main.user_backend_v2.entity.report;

import com.dsmpear.main.user_backend_v2.entity.BaseEntity;
import com.dsmpear.main.user_backend_v2.entity.comment.Comment;
import com.dsmpear.main.user_backend_v2.entity.language.Language;
import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.reportfile.ReportFile;
import com.dsmpear.main.user_backend_v2.entity.reporttype.ReportType;
import com.dsmpear.main.user_backend_v2.payload.request.ReportRequest;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report_tbl")
@Entity
public class Report extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Builder.Default
    @Column(name = "is_accepted", nullable = false)
    private Boolean isAccepted = false;

    @Column(name = "is_submitted", nullable = false)
    private Boolean isSubmitted;

    @Column(nullable = false, name = "team_name")
    private String teamName;

    private String comment;

    private String github;


    //  관계매핑
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy ="report", fetch = FetchType.EAGER)
    @JsonBackReference
    private ReportType reportType;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "report", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Language> languages;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "report", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Member> members;

    @OneToOne(mappedBy = "report", fetch = FetchType.LAZY)
    @JsonBackReference
    private ReportFile reportFile;

    @OneToMany(mappedBy = "report", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Comment> comments;

    public void update(ReportRequest reportRequest) {
        this.title = reportRequest.getTitle();
        this.description = reportRequest.getDescription();
        this.isSubmitted = reportRequest.getIsSubmitted();
        this.github = reportRequest.getGithub();
        this.teamName = reportRequest.getTeamName();
    }

}
