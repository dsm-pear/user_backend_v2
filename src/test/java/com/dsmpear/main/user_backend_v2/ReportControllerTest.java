package com.dsmpear.main.user_backend_v2;

import com.dsmpear.main.user_backend_v2.entity.language.Language;
import com.dsmpear.main.user_backend_v2.entity.language.LanguageRepository;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Field;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Grade;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Type;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.payload.request.ReportRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @BeforeEach
    void setUp() {
        mvc = basicTestSupport.setUp();

        basicTestSupport.createUser("email");

        basicTestSupport.createReport("title_for_every", true, true, Access.EVERY);
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
}
