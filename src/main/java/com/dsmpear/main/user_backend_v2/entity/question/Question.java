package com.dsmpear.main.user_backend_v2.entity.question;

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
public class Question{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 30)
    @Email
    private String email;

    @Column(nullable = false, length = 150)
    private String description;

}
