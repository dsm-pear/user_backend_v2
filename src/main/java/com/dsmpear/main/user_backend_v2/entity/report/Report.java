package com.dsmpear.main.user_backend_v2.entity.report;

import com.dsmpear.main.user_backend_v2.entity.BaseCreatedAtEntity;
import com.dsmpear.main.user_backend_v2.entity.comment.Comment;
import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.report.embedded.Status;
import com.dsmpear.main.user_backend_v2.entity.reportfile.ReportFile;
import com.dsmpear.main.user_backend_v2.entity.reporttype.ReportType;
import com.dsmpear.main.user_backend_v2.payload.request.report.BaseReportRequest;
import com.dsmpear.main.user_backend_v2.payload.request.report.SoleReportRequest;
import com.dsmpear.main.user_backend_v2.payload.request.report.TeamReportRequest;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report_tbl")
@Entity
public class Report extends BaseCreatedAtEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "team_name")
    private String teamName;

    private String comment;

    private String github;

    @Embedded
    private Status status;

    //  관계매핑
    @Setter
    @OneToOne(cascade = CascadeType.ALL, mappedBy ="report", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private ReportType reportType;

    @Builder.Default
    @ElementCollection  // 컬렉션 값 타입은, 부모에게 생명 주기가 완전히 종속되어 있고, 만약 변경된다면 모두 지운 후 다시 저장된다
    @CollectionTable(name = "language_tbl", joinColumns = @JoinColumn(name = "report_id"))
    private final List<String> languages = new ArrayList<>();

    // 원래 builder 패턴을 사용한데다가 따로 setter를 사용하지 않아서 초기화가 필요 없었지만, add를 해주기 위해 초기화가 필요하다
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "report", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private final List<Member> members = new ArrayList<>();

    @OneToOne(mappedBy = "report", fetch = FetchType.LAZY)
    @JsonManagedReference
    private ReportFile reportFile;

    @OneToMany(mappedBy = "report", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private final List<Comment> comments = new ArrayList<>();

    // getters
    public List<String> getLanguages() {
        return Collections.unmodifiableList(languages);
    }

    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    // update methods
    public <R extends BaseReportRequest>void update(R reportRequest) {
        this.title = reportRequest.getTitle();
        this.description = reportRequest.getDescription();
        this.github = reportRequest.getGithub();
        if (!(reportRequest instanceof SoleReportRequest))
            this.teamName = ((TeamReportRequest)reportRequest).getTeamName();
    }

    public void addLanguage(List<String> language) {
        this.languages.clear();
        this.languages.addAll(language);
    }

    public void addMember(List<Member> members) {
        this.members.clear();
        this.members.addAll(members);
    }

}