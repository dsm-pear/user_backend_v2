package com.dsmpear.main.user_backend_v2.entity.reporttype;

import com.dsmpear.main.user_backend_v2.entity.report.*;
import com.dsmpear.main.user_backend_v2.entity.report.Access;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "report_type_tbl")
public class ReportType {

    @Id
    private Long reportId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Access access;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Field field;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;
}
