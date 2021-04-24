package com.dsmpear.main.user_backend_v2;

import com.dsmpear.main.user_backend_v2.entity.language.Language;
import com.dsmpear.main.user_backend_v2.entity.language.LanguageRepository;
import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.member.MemberRepository;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Field;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Grade;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Type;
import com.dsmpear.main.user_backend_v2.entity.reporttype.ReportType;
import com.dsmpear.main.user_backend_v2.entity.reporttype.ReportTypeRepository;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.exception.UserNotFoundException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserBackendV2Application.class)
@ActiveProfiles("test")
class MyPageControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportTypeRepository reportTypeRepository;

    @Autowired
    private LanguageRepository languageRepository;

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
                        .password(passwordEncoder.encode("1111"))
                        .authStatus(true)
                        .selfIntro("lalalala")
                        .build()
        );
        userRepository.save(
                User.builder()
                        .email("tset@dsm.hs.kr")
                        .name("고길동")
                        .password(passwordEncoder.encode("1111"))
                        .authStatus(true)
                        .selfIntro("lalalala")
                        .build()
        );
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
        write_report("test@dsm.hs.kr");
        write_report("test@dsm.hs.kr");

        mvc.perform(get("/user/profile/report")
                .param("size","6")
                .param("page", "0"))
                .andExpect(status().isOk()).andDo(print());
    }

    private Report write_report(String email) {
        User user = userRepository.findById(email)
                .orElseThrow(UserNotFoundException::new);

        Report report = reportRepository.save(
                Report.builder()
                        .title("hello")
                        .description("hihello")
                        .isSubmitted(false)
                        .isAccepted(false)
                        .comment("반환합니다")
                        .github("https://github.com")
                        .teamName("룰루랄라")
                        .build()
        );

        Member member = memberRepository.save(
                Member.builder()
                        .report(report)
                        .user(user)
                        .build()
        );

        ReportType reportType = reportTypeRepository.save(
                ReportType.builder()
                    .reportId(report.getId())
                    .type(Type.CIRCLES)
                    .access(Access.ADMIN)
                    .field(Field.WEB)
                    .grade(Grade.GRADE2)
                    .report(report)
                    .build()
        );

        Language language1 = languageRepository.save(
                Language.builder()
                        .language("C")
                        .report(report)
                        .build()
        );

        Language language2 = languageRepository.save(
                Language.builder()
                        .language("JAVA")
                        .report(report)
                        .build()
        );

        List<Language> languages = List.of(language1,language2);

        report = reportRepository.save(
                Report.builder()
                        .members((List<Member>)member)
                        .reportType(reportType)
                        .languages(languages)
                        .build()
        );

        return report;
    }

}