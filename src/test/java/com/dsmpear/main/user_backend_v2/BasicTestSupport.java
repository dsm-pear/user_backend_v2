package com.dsmpear.main.user_backend_v2;

import com.dsmpear.main.user_backend_v2.entity.comment.CommentRepository;
import com.dsmpear.main.user_backend_v2.entity.language.LanguageRepository;
import com.dsmpear.main.user_backend_v2.entity.notice.NoticeRepository;
import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@Component
public class BasicTestSupport {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

}
