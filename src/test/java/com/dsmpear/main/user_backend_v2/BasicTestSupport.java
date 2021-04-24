package com.dsmpear.main.user_backend_v2;

import com.dsmpear.main.user_backend_v2.entity.comment.CommentRepository;
import com.dsmpear.main.user_backend_v2.entity.language.Language;
import com.dsmpear.main.user_backend_v2.entity.language.LanguageRepository;
import com.dsmpear.main.user_backend_v2.entity.notice.NoticeRepository;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Component
public class BasicTestSupport {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportTypeRepository reportTypeRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    public MockMvc setUp() {
        return MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    public <T extends CrudRepository> void cleanUp(T repository) {
        repository.deleteAll();
    }

    public User createUser(String email) {
        return userRepository.save(
                User.builder()
                        .password(passwordEncoder.encode("pwd"))
                        .email(email)
                        .authStatus(true)
                        .name("name")
                        .gitHub("github")
                        .selfIntro("self")
                        .build()
        );
    }

    public Report createReport(String title, Boolean isAccepted, Boolean isSubmitted, Access access) {
        Report report = Report.builder()
                        .description("desc")
                        .isAccepted(isAccepted)
                        .title(title)
                        .isSubmitted(isSubmitted)
                        .github("gitthub")
                        .teamName("teamName")
                        .build();

        reportTypeRepository.save(
                ReportType.builder()
                        .report(report)
                        .access(access)
                        .field(Field.WEB)
                        .type(Type.TEAM)
                        .grade(Grade.GRADE1)
                        .build()
        );
        for (int i = 0; i < 4; i++) {
            languageRepository.save(
                    Language.builder()
                            .language("language"+i)
                            .report(report)
                            .build()
            );
        }

        return report;
    }

    public <T> String writeValueAsString(T request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }

}
