package com.dsmpear.main.user_backend_v2.entity.report;

import com.dsmpear.main.user_backend_v2.entity.BaseEntity;
import com.dsmpear.main.user_backend_v2.entity.comment.Comment;
import com.dsmpear.main.user_backend_v2.entity.language.Language;
import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.reportfile.ReportFile;
import com.dsmpear.main.user_backend_v2.entity.reporttype.ReportType;
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
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "is_accepted", nullable = false)
    private Boolean isAccepted;

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

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "report", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Member> members;

    @OneToOne(mappedBy = "report", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonBackReference
    private ReportFile reportFile;

    @OneToMany(mappedBy = "report", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Comment> comments;

    /*public Report update(ReportRequest reportRequest) {
        this.title = reportRequest.getTitle();
        this.description = reportRequest.getDescription();
        this.languages = reportRequest.getLanguages();
        this.type = reportRequest.getType();
        this.access = reportRequest.getAccess();
        this.field = reportRequest.getField();
        this.grade = reportRequest.getGrade();
        this.isSubmitted = reportRequest.getIsSubmitted();
        this.fileName = reportRequest.getFileName();
        this.github = reportRequest.getGithub();
        return this;
    }*/

}
