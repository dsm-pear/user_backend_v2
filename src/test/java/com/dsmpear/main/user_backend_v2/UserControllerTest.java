package com.dsmpear.main.user_backend_v2;

import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserBackendV2Application.class)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mvc;

    @BeforeEach
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        userRepository.save(
                User.builder()
                        .email("apple@dsm.hs.kr")
                        .name("홍길동")
                        .password(passwordEncoder.encode("1234"))
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email("alreadyuser@dsm.hs.kr")
                        .password(passwordEncoder.encode("1111"))
                        .name("alreadyuser")
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email("bear@dsm.hs.kr")
                        .name("고jam")
                        .password(passwordEncoder.encode("1234"))
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email("cat@dsm.hs.kr")
                        .name("양jam")
                        .password(passwordEncoder.encode("1234"))
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email("dear@dsm.hs.kr")
                        .name("강jam")
                        .password(passwordEncoder.encode("1234"))
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email("dear123456@dsm.hs.kr")
                        .name("강jam")
                        .password(passwordEncoder.encode("1234"))
                        .build()
        );

    }

    @Test
    @WithMockUser(value = "apple@dsm.hs.kr",password = "1111")
    public void getUser() throws Exception{
        mvc.perform(get("/account?name="))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "apple@dsm.hs.kr",password = "1111")
    public void getUser_existUser() throws Exception{
        mvc.perform(get("/account?name=홍길동"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUser_noLogin() throws Exception{
        mvc.perform(get("/account?name="))
                .andExpect(status().isUnauthorized());
    }

}
