package com.dsmpear.main.user_backend_v2.entity.report;

import com.dsmpear.main.user_backend_v2.entity.BaseEntity;
import com.dsmpear.main.user_backend_v2.entity.comment.Comment;
import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Field;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Grade;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Type;
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
    @OneToOne(cascade = CascadeType.ALL, mappedBy ="report", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private ReportType reportType;

    @ElementCollection  // 컬렉션 값 타입은, 부모에게 생명 주기가 완전히 종속되어 있고, 만약 변경된다면 모두 지운 후 다시 저장된다
    @CollectionTable(name = "language_tbl", joinColumns = @JoinColumn(name = "report_id"))
    private List<String> languages = new ArrayList<>();

    // 원래 builder 패턴을 사용한데다가 따로 setter를 사용하지 않아서 초기화가 필요 없었지만, add를 해주기 위해 초기화가 필요하다
    @Setter
    @Builder.Default
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, mappedBy = "report", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Member> members = new ArrayList<>();

    @OneToOne(mappedBy = "report", fetch = FetchType.LAZY)
    @JsonManagedReference
    private ReportFile reportFile;

    @OneToMany(mappedBy = "report", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments;

    public void update(ReportRequest reportRequest) {
        this.title = reportRequest.getTitle();
        this.description = reportRequest.getDescription();
        this.github = reportRequest.getGithub();
        this.teamName = reportRequest.getTeamName();
    }

    public void addMember(Member member) {
        this.members.add(member);
    }

}
