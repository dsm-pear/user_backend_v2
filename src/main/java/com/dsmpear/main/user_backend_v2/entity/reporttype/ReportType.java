package com.dsmpear.main.user_backend_v2.entity.reporttype;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.enums.*;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.payload.request.ReportRequest;
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

    public void update(ReportRequest request) {
        this.access = request.getAccess();
        this.grade = request.getGrade();
        this.field = request.getField();
        this.type = request.getType();
    }
}
