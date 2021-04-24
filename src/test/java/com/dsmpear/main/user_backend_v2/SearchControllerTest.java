
package com.dsmpear.main.user_backend_v2;

import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.payload.response.SearchProfileResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserBackendV2Application.class)
@ActiveProfiles("test")
class SearchControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mvc;

    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        userRepository.save(
                User.builder()
                        .email("test@dsm.hs.kr")
                        .name("홍길동")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(true)
                        .selfIntro("hihihihi")
                        .build()
        );
        userRepository.save(
                User.builder()
                        .email("tset@dsm.hs.kr")
                        .name("고길동")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(true)
                        .selfIntro("lalalala")
                        .build()
        );
        userRepository.save(
                User.builder()
                        .email("ptest@dsm.hs.kr")
                        .name("김길동")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(true)
                        .selfIntro("lalalala")
                        .build()
        );
        userRepository.save(
                User.builder()
                        .email("etest@dsm.hs.kr")
                        .name("이길동")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(true)
                        .selfIntro("lalalala")
                        .build()
        );

    }

    @AfterEach
    public void after () {
        userRepository.deleteAll();
        reportRepository.deleteAll();
    }

    @Test
    public void searchProfile () throws Exception {
        mvc.perform(get("/search/profile?keyword=길동&size=10&page=0"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchProfile_noKeyword () throws Exception {
        mvc.perform(get("/search/profile?keyword=&size=10&page=0"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchProfile_full () throws Exception {
        mvc.perform(get("/search/profile?keyword=이길동&size=10&page=0"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchProfile_notFound () throws Exception {
        MvcResult result = mvc.perform(get("/search/profile?keyword=동글이&size=10&page=0"))
                .andReturn();

        SearchProfileResponse response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), SearchProfileResponse.class);
        Assertions.assertEquals(response.getTotalElements().longValue(), 0L);
    }


}