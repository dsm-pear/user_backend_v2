package com.dsmpear.main.user_backend_v2;

import com.dsmpear.main.user_backend_v2.entity.report.ReportRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ReportControllerTest {

    @Autowired
    private ReportRepository reportRepository;


}
