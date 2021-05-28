package com.dsmpear.main.user_backend_v2;

import com.dsmpear.main.user_backend_v2.entity.member.MemberRepository;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.repository.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Field;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Grade;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Type;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.exception.ReportNotFoundException;
import com.dsmpear.main.user_backend_v2.payload.request.report.SoleReportRequest;
import com.dsmpear.main.user_backend_v2.payload.request.report.TeamReportRequest;
import com.dsmpear.main.user_backend_v2.payload.response.ReportContentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ReportControllerTest {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BasicTestSupport basicTestSupport;

    private MockMvc mvc;

    Report successReport;

    @BeforeEach
    void setUp() {
        mvc = basicTestSupport.setUp();

        basicTestSupport.createUser("email");
        basicTestSupport.createUser("email2");
        basicTestSupport.createUser("test@dsm.hs.kr");
        basicTestSupport.createUser("test2@dsm.hs.kr");

        successReport = basicTestSupport.createReport("title_for_every", true, true, Access.EVERY);
        basicTestSupport.createReport("title_for_not_shown", false, true, Access.EVERY);
        basicTestSupport.createReport("title_for_not_shown", false, false, Access.EVERY);
        basicTestSupport.createReport("title_for_not_shown", true, true, Access.ADMIN);
    }

    @AfterEach
    void cleanUp() {
        basicTestSupport.cleanUp(reportRepository);
        basicTestSupport.cleanUp(userRepository);
    }

    @Test
    @WithMockUser(value = "email", password = "pwd")
    void 보고서_개인_작성_성공() throws Exception {
         SoleReportRequest request = buildSoleRequest("title");

        mvc.perform(post("/report/sole")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(basicTestSupport.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(value = "email", password = "pwd")
    void 보고서_팀_작성_성공() throws Exception {
         TeamReportRequest request = buildTeamRequest("title");

        MvcResult result = mvc.perform(post("/report/team")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(basicTestSupport.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        Report report = reportRepository.findById(
                new ObjectMapper().readValue(result.getResponse().getContentAsString(), Long.class))
                .orElseThrow(ReportNotFoundException::new);

        Assertions.assertNotEquals(report.getTeamName(), "");
        Assertions.assertNotEquals(report.getTeamName(), null);

    }

    @Test
    @WithMockUser(value = "test@dsm.hs.kr", password = "pwd")
    void 보고서_개인_수정_성공() throws Exception {
        SoleReportRequest request = buildSoleRequest("title");

        mvc.perform(patch("/report/sole/" + successReport.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(basicTestSupport.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(value = "email", password = "pwd")
    void 보고서_개인_수정_실패() throws Exception {
        SoleReportRequest request = buildSoleRequest("title");

        mvc.perform(patch("/report/sole/" + successReport.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(basicTestSupport.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = "test2@dsm.hs.kr", password = "pwd")
    void 보고서_팀_수정_성공() throws Exception {
        TeamReportRequest request = buildTeamRequest("title");

        mvc.perform(post("/report/team/" + successReport.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(basicTestSupport.writeValueAsString(request)))
                .andExpect(status().isCreated());

        Assertions.assertEquals(memberRepository.findAllByReport(successReport).size(), 3);
    }

    @Test
    @WithMockUser(value = "email", password = "pwd")
    void 보고서_보기_성공() throws Exception {
        MvcResult result = mvc.perform(get("/report/"+successReport.getId()))
                .andExpect(status().isOk())
                .andReturn();

        ReportContentResponse response = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .readValue(result.getResponse().getContentAsString(), ReportContentResponse.class);

        Assertions.assertEquals("title_for_every", response.getTitle());
        Assertions.assertTrue(response.getIsSubmitted());
    }

    @Test
    void 보고서_보기_실패_유저_NOTFOUND() throws Exception {
        mvc.perform(get("/report/"+1))
                .andExpect(status().isNotFound());      // 유저를 찿지 못해서 NOT FOUND
    }

    @Test
    @WithMockUser(value = "email", password = "pwd")
    void 보고서_보기_실패_보고서_NOTFOUND() throws Exception {
        mvc.perform(get("/report/"+1000))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(value = "test@dsm.hs.kr", password = "pwd")
    void 보고서_삭제_성공() throws Exception {
        mvc.perform(delete("/report/"+successReport.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "email2", password = "pwd")
    void 보고서_삭제_실패_다른유저() throws Exception {
        mvc.perform(delete("/report/"+successReport.getId()))
                .andExpect(status().isUnauthorized());
    }

    private SoleReportRequest buildSoleRequest(String title) {
        return SoleReportRequest.builder()
                .title(title)
                .type(Type.TEAM)
                .languages(Arrays.asList("sdf","asdfsa"))
                .isSubmitted(true)
                .grade(Grade.GRADE1)
                .github("github.com")
                .field(Field.WEB)
                .description("description")
                .access(Access.EVERY)
                .build();
    }

    private TeamReportRequest buildTeamRequest(String title) {
        return TeamReportRequest.builder()
                .teamName("teamName11")
                .members(Arrays.asList("email", "email2"))
                .title(title)
                .type(Type.TEAM)
                .languages(Arrays.asList("sdf","asdfsa"))
                .isSubmitted(true)
                .grade(Grade.GRADE1)
                .github("github.com")
                .field(Field.WEB)
                .description("description")
                .access(Access.EVERY)
                .build();
    }

}
