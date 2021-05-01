package com.dsmpear.main.user_backend_v2;

import com.dsmpear.main.user_backend_v2.entity.comment.Comment;
import com.dsmpear.main.user_backend_v2.entity.comment.CommentRepository;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.payload.request.CommentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserBackendV2Application.class)
@ActiveProfiles("test")
public class CommentControllerTest {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BasicTestSupport basicTestSupport;

    @Autowired
    private CommentRepository commentRepository;

    private MockMvc mvc;

    Report successReport;

    @BeforeEach
    void setUp() {
        mvc = basicTestSupport.setUp();

        basicTestSupport.createUser("email");
        basicTestSupport.createUser("email2");
        basicTestSupport.createUser("test@dsm.hs.kr");
        basicTestSupport.createUser("test1@dsm.hs.kr");

        successReport = basicTestSupport.createReport("title_for_every", true, true, Access.EVERY);
        basicTestSupport.createReport("title_for_not_shown", false, true, Access.EVERY);
        basicTestSupport.createReport("title_for_not_shown", false, false, Access.EVERY);
        basicTestSupport.createReport("title_for_not_shown", true, true, Access.ADMIN);
    }

    @AfterEach
    void cleanUp() {
        basicTestSupport.cleanUp(reportRepository);
        basicTestSupport.cleanUp(userRepository);
        basicTestSupport.cleanUp(commentRepository);
    }

    // 댓글 작성 성공 테스트
    @Test
    @WithMockUser(value = "test@dsm.hs.kr", password = "1234")
    public void createComment() throws Exception {
        createComment(successReport);
        createComment(successReport);
        createComment(successReport);

        CommentRequest request = CommentRequest.builder()
                .content("아이야아이야")
                .build();

        mvc.perform(post("/comment/"+successReport.getId())
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

    }

    // 댓글 작성 실패 테스트
    @Test
    public void createComment1() throws Exception {
        createComment(successReport);
        createComment(successReport);
        createComment(successReport);

        CommentRequest request = CommentRequest.builder()
                .content("아이야아이야")
                .build();

        mvc.perform(post("/comment/"+successReport.getId())
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());

    }

    // 댓글 수정 성공 테스트
    @Test
    @WithMockUser(value = "test@dsm.hs.kr", password = "1234")
    public void updateComment() throws Exception {

        createComment(successReport);
        createComment(successReport);
        Integer commentId = createComment(successReport);

        CommentRequest request = CommentRequest.builder()
                .content("아이야아이야")
                .build();
        mvc.perform(patch("/comment/"+commentId)
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(value = "test22@dsm.hs.kr", password = "1234")
    public void updateComment2() throws Exception {
        createComment(successReport);
        createComment(successReport);
        createComment(successReport);
        Integer commentId1 = createComment(successReport);

        CommentRequest request = CommentRequest.builder()
                .content("아이야아이야")
                .build();

        mvc.perform(patch("/comment/"+commentId1)
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());

    }


    // 댓글 수정 실패 테스트
    @Test
    public void updateComment1() throws Exception {
        createComment(successReport);
        createComment(successReport);
        createComment(successReport);
        Integer commentId1 = createComment(successReport);

        CommentRequest request = CommentRequest.builder()
                .content("아이야아이야")
                .build();

        mvc.perform(patch("/comment/"+commentId1)
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());

    }

    // 댓글 수정 실패 테스트
    @Test
    @WithMockUser(value = "test@dsm.hs.kr", password = "1234")
    public void updateComment3() throws Exception {
        createComment(successReport);
        createComment(successReport);
        createComment(successReport);
        CommentRequest request = CommentRequest.builder()
                .content("아이야아이야")
                .build();

        mvc.perform(patch("/comment/"+200)
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());

    }

    // 댓글 수정 실패 테스트
    @Test
    @WithMockUser(value = "test2@dsm.hs.kr", password = "1234")
    public void updateComment4() throws Exception {
        createComment(successReport);
        createComment(successReport);
        createComment(successReport);
        Integer commentId = createComment(successReport);

        CommentRequest request = CommentRequest.builder()
                .content("아이야아이야")
                .build();

        mvc.perform(patch("/comment/"+commentId)
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());

    }

    // 댓글 삭제 성공 테스트
    @Test
    @WithMockUser(value = "test@dsm.hs.kr", password = "1234")
    public void deleteComment() throws Exception {
        createComment(successReport);
        createComment(successReport);
        Integer commentId = createComment(successReport);

        mvc.perform(delete("/comment/{commentId}", commentId))
                .andExpect(status().isOk());

    }


    @Test
    @WithMockUser(value = "test1@dsm.hs.kr", password = "1234")
    public void deleteComment1() throws Exception {
        createComment(successReport);
        createComment(successReport);
        Integer commentId = createComment(successReport);

        mvc.perform(delete("/comment/"+commentId))
                .andExpect(status().isUnauthorized());

    }


    @Test
    public void deleteComment2() throws Exception {
        createComment(successReport);
        createComment(successReport);
        createComment(successReport);
        Integer commentId1 = createComment(successReport);

        mvc.perform(delete("/comment/"+commentId1))
                .andExpect(status().isUnauthorized());

    }

    private Integer createComment(Report report) {
        return commentRepository.save(
                Comment.builder()
                        .report(report)
                        .content("아이야아이야")
                        .user(userRepository.findByEmail("test@dsm.hs.kr").get())
                        .build()
        ).getId().intValue();
    }

}
