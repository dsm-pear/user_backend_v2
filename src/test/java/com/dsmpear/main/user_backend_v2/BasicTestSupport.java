package com.dsmpear.main.user_backend_v2;

import com.dsmpear.main.user_backend_v2.entity.member.Member;
import com.dsmpear.main.user_backend_v2.entity.member.MemberRepository;
import com.dsmpear.main.user_backend_v2.entity.report.Report;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

@Component
public class BasicTestSupport {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportTypeRepository reportTypeRepository;

    @Autowired
    private MemberRepository memberRepository;

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
                        .languages(Arrays.asList("dsaf","asdf"))
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

        addMember(report, "test@dsm.hs.kr");

        return report;
    }

    public Member addMember(Report report, String userEmail) {
        return memberRepository.save(
                Member.builder()
                        .report(report)
                        .user(createUser(userEmail))
                        .build()
        );
    }

    public <T> String writeValueAsString(T request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }

}
