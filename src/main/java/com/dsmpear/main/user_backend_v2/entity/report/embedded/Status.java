package com.dsmpear.main.user_backend_v2.entity.report.embedded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Status {

    @Builder.Default
    @Column(name = "is_accepted", nullable = false)
    private Boolean isAccepted = true;

    @Column(name = "is_submitted", nullable = false)
    private Boolean isSubmitted;

}
