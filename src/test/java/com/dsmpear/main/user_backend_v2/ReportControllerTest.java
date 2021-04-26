package com.dsmpear.main.user_backend_v2;

import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Field;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Grade;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Type;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.payload.request.ReportRequest;
import com.dsmpear.main.user_backend_v2.payload.response.ProfileReportsResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ReportControllerTest {

    @Autowired
    private ReportRepository reportRepository;

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
    void 보고서_작성_성공() throws Exception {
        ReportRequest request = ReportRequest.builder()
                .title("new title")
                .type(Type.TEAM)
                .teamName("team")
                .languages(Arrays.asList("sdf","asdfsa"))
                .isSubmitted(true)
                .grade(Grade.GRADE1)
                .github("sf")
                .field(Field.WEB)
                .description("description")
                .access(Access.EVERY)
                .description("description")
                .members(Arrays.asList("email", "email2", "test@dsm.hs.kr"))
                .build();

        mvc.perform(post("/report")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(basicTestSupport.writeValueAsString(request)))
                .andExpect(status().isCreated());

        Report report = reportRepository.findAllBy().get(4);

        Assertions.assertEquals(report.getTitle(), "new title");
        Assertions.assertEquals(report.getReportType().getType(), Type.TEAM);
    }

    @Test
    void 보고서_작성_권한실패() throws Exception {
        ReportRequest request = ReportRequest.builder()
                .title("new title")
                .type(Type.TEAM)
                .teamName("team")
                .languages(Arrays.asList("sdf","asdfsa"))
                .isSubmitted(true)
                .grade(Grade.GRADE1)
                .github("sf")
                .field(Field.WEB)
                .description("description")
                .access(Access.EVERY)
                .description("description")
                .build();

        mvc.perform(post("/report")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(basicTestSupport.writeValueAsString(request)))
                .andExpect(status().isNotFound());
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
}
