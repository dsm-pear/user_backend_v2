package com.dsmpear.main.user_backend_v2.entity.notice;

import com.dsmpear.main.user_backend_v2.entity.BaseCreatedAtEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notice_tbl")
@Entity
public class Notice extends BaseCreatedAtEntity {

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

}
