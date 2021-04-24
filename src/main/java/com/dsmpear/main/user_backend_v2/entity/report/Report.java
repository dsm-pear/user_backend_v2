package com.dsmpear.main.user_backend_v2.entity.report;

import com.dsmpear.main.user_backend_v2.entity.BaseEntity;
import com.dsmpear.main.user_backend_v2.entity.comment.Comment;
import com.dsmpear.main.user_backend_v2.entity.language.Language;
import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.reportfile.ReportFile;
import com.dsmpear.main.user_backend_v2.entity.reporttype.ReportType;
import com.dsmpear.main.user_backend_v2.payload.request.ReportRequest;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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
    @Setter
    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, mappedBy ="report", fetch = FetchType.EAGER)
    @JsonManagedReference
    private ReportType reportType;

    // 원래 builder 패턴을 사용한데다가 따로 setter를 사용하지 않아서 초기화가 필요 없었지만, add를 해주기 위해 초기화가 필요하다
    @Setter
    @Builder.Default
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, mappedBy = "report", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Language> languages = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "report", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Member> members = new ArrayList<>();

    @OneToOne(mappedBy = "report", fetch = FetchType.LAZY)
    @JsonManagedReference
    private ReportFile reportFile;

    @OneToMany(mappedBy = "report", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Comment> comments;

    public void update(ReportRequest reportRequest) {
        this.title = reportRequest.getTitle();
        this.description = reportRequest.getDescription();
        this.isSubmitted = reportRequest.getIsSubmitted();
        this.github = reportRequest.getGithub();
        this.teamName = reportRequest.getTeamName();
    }

    public void addMember(Member member) {
        this.members.add(member);
    }

    public void addLanguage(Language language) {
        this.languages.add(language);
    }
}
