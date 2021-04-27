package com.dsmpear.main.user_backend_v2.entity.user;

import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.comment.Comment;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tbl")
@Entity
public class User {

    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(name = "self_intro")
    private String selfIntro;

    @Column(name = "github")
    private String gitHub;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Member> member;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Comment> comments;


    public void updateInfo(String intro, String gitHub){
        this.selfIntro = intro;
        this.gitHub = gitHub;
    }

}
