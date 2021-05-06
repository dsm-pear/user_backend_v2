package com.dsmpear.main.user_backend_v2.entity.comment;

import com.dsmpear.main.user_backend_v2.entity.BaseCreatedAtEntity;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment_tbl")
@Entity
public class Comment extends BaseCreatedAtEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    @JsonBackReference
    private Report report;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    @JsonBackReference
    private User user;

    @Column(nullable = false)
    private String content;

    public void updateContent(String content) {
        this.content = content;
    }

}

