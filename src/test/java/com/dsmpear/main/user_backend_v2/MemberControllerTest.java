package com.dsmpear.main.user_backend_v2;

import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.member.MemberRepository;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.payload.request.MemberRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserBackendV2Application.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberControllerTest {

    @Autowired
    private WebApplicationContext context;

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

    private Member member1;
    private Member member2;

    @BeforeEach
    public void setUp(){
        mvc = basicTestSupport.setUp();

        report1 = basicTestSupport.createReport("title_for_not_shown", false, false, Access.EVERY);
        report2 = basicTestSupport.createReport("title_for_not_shown", false, false, Access.ADMIN);
        member1 = basicTestSupport.addMember(report1, "test1@dsm.hs.kr");
        member2 = basicTestSupport.addMember(report2, "test2@dsm.hs.kr");
        basicTestSupport.addMember(report1, "test3@dsm.hs.kr");
    }


    @AfterEach
    public void after () {
        memberRepository.deleteAll();
        reportRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "test1@dsm.hs.kr",password = "1111")
    public void addMember() throws Exception {
        MemberRequest request = new MemberRequest(report1.getId(),"test2@dsm.hs.kr");

        mvc.perform(post("/member").
                content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "test1@dsm.hs.kr",password = "1111")
    public void addMember_already() throws Exception {

        MemberRequest request = new MemberRequest(report1.getId(),"test3@dsm.hs.kr");

        mvc.perform(post("/member").
                content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "test1@dsm.hs.kr",password = "1111")
    public void addMember_notmember() throws Exception {
        MemberRequest request = new MemberRequest(report2.getId(),"test3@dsm.hs.kr");
        mvc.perform(post("/member").
                content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden());
    }

    //로그인하지 않았을 때
    @Test
    @WithMockUser()
    public void addMember_noLogin() throws Exception {
        MemberRequest request = new MemberRequest(report1.getId(),"test2@dsm.hs.kr");
        mvc.perform(post("/member").
                content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(value = "test3@dsm.hs.kr",password = "1111")
    public void deleteMember() throws Exception{
        mvc.perform(delete("/member/"+member1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "test1@dsm.hs.kr",password = "1111")
    public void deleteMember_me() throws Exception{

        mvc.perform(delete("/member/"+member1.getId()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteMember_noLogin() throws Exception {
        mvc.perform(delete("/member/"+member2.getId()))
                .andExpect(status().isUnauthorized());
    }

}