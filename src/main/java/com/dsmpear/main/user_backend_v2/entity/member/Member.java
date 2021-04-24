package com.dsmpear.main.user_backend_v2.entity.member;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Member {

    @Id @GeneratedValue
    private Integer id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_email")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "report_id")
    private Report report;

}
