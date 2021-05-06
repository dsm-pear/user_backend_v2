package com.dsmpear.main.user_backend_v2.entity.member;

import com.dsmpear.main.user_backend_v2.entity.BaseIdEntity;
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
@Table(name = "member_tbl")
@Entity
public class Member extends BaseIdEntity {

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_email")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "report_id")
    private Report report;

}
