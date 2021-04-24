package com.dsmpear.main.user_backend_v2;

import com.dsmpear.main.user_backend_v2.entity.language.LanguageRepository;
import com.dsmpear.main.user_backend_v2.entity.member.MemberRepository;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.reporttype.ReportTypeRepository;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.payload.request.SetSelfIntroRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserBackendV2Application.class)
@ActiveProfiles("test")
class MyPageControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BasicTestSupport basicTestSupport;

    private MockMvc mvc;

    @BeforeEach
    public void setUp() throws Exception {
        mvc = basicTestSupport.setUp();

        basicTestSupport.createUser("test@dsm.hs.kr");
        basicTestSupport.createReport("title_for_every", true, true, Access.EVERY);
        basicTestSupport.createReport("title_for_not_shown", false, true, Access.EVERY);
    }

    @AfterEach
    public void after() {
        memberRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "test@dsm.hs.kr", password = "1111")
    public void getMyProfile_test() throws Exception {
        mvc.perform(get("/user/profile"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "tset@dsm.hs.kr", password = "1111")
    public void getMyProfile_tset() throws Exception {
        mvc.perform(get("/user/profile"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "test@dsm.hs.kr", password = "1111")
    public void modifySelfIntro_test() throws Exception {

        String expectedGithub = "https://github.com/syxxn";
        String expectedIntro = "introduce";
        SetSelfIntroRequest request = SetSelfIntroRequest.builder()
                .github(expectedGithub)
                .intro(expectedIntro)
                .build();

        mvc.perform(put("/user/profile")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());

        Assertions.assertEquals(userRepository.findByEmail("test@dsm.hs.kr").get().getSelfIntro(), expectedIntro);
        Assertions.assertEquals(userRepository.findByEmail("test@dsm.hs.kr").get().getGitHub(), expectedGithub);
    }

    @Test
    @WithMockUser(value = "test@dsm.hs.kr", password = "1111")
    public void getReports() throws Exception {
        mvc.perform(get("/user/profile/report")
                .param("size","6")
                .param("page", "0"))
                .andExpect(status().isOk()).andDo(print());
    }

}