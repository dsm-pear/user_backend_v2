package com.dsmpear.main.user_backend_v2.entity.question;

import com.dsmpear.main.user_backend_v2.entity.BaseIdEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question_tbl")
@Entity
public class Question extends BaseIdEntity {

    @Email
    @Column(nullable = false,length = 30)
    private String email;

    @Column(nullable = false, length = 150)
    private String description;

}
