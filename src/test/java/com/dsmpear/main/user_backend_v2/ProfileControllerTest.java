package com.dsmpear.main.user_backend_v2;

import com.dsmpear.main.user_backend_v2.entity.member.MemberRepository;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.payload.response.ProfileReportsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserBackendV2Application.class)
@ActiveProfiles("test")
public class ProfileControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private BasicTestSupport basicTestSupport;

    private MockMvc mvc;

    private Report report1;
    private Report report2;
    private Report report3;

    private User user;

    @BeforeEach
    public void setUp() throws Exception {
        mvc = basicTestSupport.setUp();

        user = basicTestSupport.createUser("test@dsm.hs.kr");
        report1 = basicTestSupport.createReport("title_for_not_shown", false, false, Access.ADMIN);
        report2 = basicTestSupport.createReport("title_for_not_shown", false, true, Access.ADMIN);
        report3 = basicTestSupport.createReport("title_for_not_shown", true, true, Access.EVERY);
        basicTestSupport.createReport("title_for_not_shown", true, true, Access.EVERY);
        basicTestSupport.addMember(report1, user.getEmail());
        basicTestSupport.addMember(report2, user.getEmail());
        basicTestSupport.addMember(report3, user.getEmail());
    }

    @AfterEach
    public void after () {
        reportRepository.deleteAll();
        memberRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void getProfile () throws Exception {
        mvc.perform(get("/profile?user-email=test@dsm.hs.kr"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@dsm.hs.kr",password = "1234")
    public void getProfile_login () throws Exception {
        mvc.perform(get("/profile?user-email=test@dsm.hs.kr"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@dsm.hs.kr",password = "1234")
    public void getProfile_noUser () throws Exception {
        mvc.perform(get("/profile"))
                .andExpect(status().isBadRequest());
    }

    //보고서 목록
    @Test
    public void getReportList() throws Exception{

        MvcResult result = mvc.perform(get("/profile/report?user-email=test@dsm.hs.kr&size=5&page=0"))
                .andExpect(status().isOk())
                .andReturn();

        ProfileReportsResponse response = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(result.getResponse().getContentAsString(), ProfileReportsResponse.class);

        Assertions.assertEquals(response.getTotalElements(), 2L);
    }

    @Test
    @WithMockUser(username = "test@dsm.hs.kr",password = "1234")
    public void  getReportList_isLogin() throws Exception{

        mvc.perform(get("/profile/report?user-email=test@dsm.hs.kr&size=2&page=0"));
    }

    @Test
    public void  getReportList_notFound() throws Exception{

        mvc.perform(get("/profile/report?user-email=lalalalala@dsm.hs.kr&size=2&page=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getReportList_notFound_isLogin() throws Exception{

        mvc.perform(get("/profile/report?user-email=lalalalal@dsm.hs.kr&size=2&page=1"))
                .andExpect(status().isNotFound());
    }

}