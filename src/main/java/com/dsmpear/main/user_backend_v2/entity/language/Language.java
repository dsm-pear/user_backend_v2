package com.dsmpear.main.user_backend_v2.entity.language;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "language_tbl")
public class Language {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 50)
    private String language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "report_id")
    private Report report;
}
