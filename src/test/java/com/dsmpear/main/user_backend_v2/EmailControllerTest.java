package com.dsmpear.main.user_backend_v2;

import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.dsmpear.main.user_backend_v2.entity.user.UserRepository;
import com.dsmpear.main.user_backend_v2.entity.verifynumber.VerifyNumber;
import com.dsmpear.main.user_backend_v2.entity.verifynumber.VerifyNumberRepository;
import com.dsmpear.main.user_backend_v2.payload.request.EmailVerifyRequest;
import com.dsmpear.main.user_backend_v2.payload.request.NotificationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Verify;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmailControllerTest {

    private MockMvc mvc;

    @Autowired
    private BasicTestSupport basicTestSupport;

    @Autowired
    private VerifyNumberRepository verifyNumberRepository;

    @BeforeEach
    public void setup() {
        mvc = basicTestSupport.setUp();
        verifyNumber();
    }

    @AfterEach
    public void after() {
        verifyNumberRepository.deleteAll();
    }

    @Test
    public void authNumEmailTestWithBadRequest() throws Exception {
        mvc.perform(get("/email/auth")
                .param("email", "smoothbear")
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void verifyAccountTest() throws Exception {
        mvc.perform(put("/email/auth")
                .content(new ObjectMapper().
                        writeValueAsString(new EmailVerifyRequest("1111", "smoothbear@dsm.hs.kr"))
                )
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void verifyAccountTestWithNumberNotFoundException() throws Exception {
        mvc.perform(put("/email/auth")
                .content(new ObjectMapper().
                        writeValueAsString(new EmailVerifyRequest("1234", "smoothbear@dsm.hs.kr"))
                )
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void notificationTestWithSecretKeyNotMatchedExcept() throws Exception {
        mvc.perform(post("/email/notification")
                .content(new ObjectMapper().writeValueAsString(new NotificationRequest("1000", "smoothbear@dsm.hs.kr", "",true))
                ).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "abcabc")
        ).andDo(print()).andExpect(status().isInternalServerError());
    }

    @Test
    public void notificationTestWithBadRequest() throws Exception {
        mvc.perform(post("/email/notification")
                .content(new ObjectMapper().writeValueAsString(NotificationRequest.builder().body("안됨").email("smoothbear@dsm.hs.kr").build())
                ).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "abcabc")
        ).andExpect(status().isBadRequest());
    }

    private void verifyNumber() {
        verifyNumberRepository.save(
                VerifyNumber.builder()
                        .email("smoothbear@dsm.hs.kr")
                        .verifyNumber("1111")
                        .build()
        );
    }

}
