package com.dsmpear.main.user_backend_v2.entity.member;

import com.dsmpear.main.user_backend_v2.entity.BaseIdEntity;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_tbl")
@EqualsAndHashCode(callSuper = false)
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
