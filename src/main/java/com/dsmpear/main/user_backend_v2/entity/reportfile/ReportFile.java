package com.dsmpear.main.user_backend_v2.entity.reportfile;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "report_file_tbl")
public class ReportFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String path;

    @Column(nullable = false, length = 50)
    private String fileName;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "report_id")
    private Report report;
}
