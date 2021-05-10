package com.dsmpear.main.user_backend_v2.entity.report.embedded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@AllArgsConstructor
@Builder
@Embeddable
public class Status {

    public Status() {
        this.isAccepted = true;
        this.isSubmitted = false;
    }

    @Column(name = "is_accepted", nullable = false)
    private Boolean isAccepted;

    @Column(name = "is_submitted", nullable = false)
    private Boolean isSubmitted;

}
