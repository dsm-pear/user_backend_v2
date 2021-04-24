package com.dsmpear.main.user_backend_v2.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(pattern = "yyyy--MM--dd HH:mm:ss", timezone = "Asia/Seoul")
public enum ErrorCode {
    INVALID_TOKEN(400, "AUTH400-0", "Invalid Token"),
    USER_NOT_FOUND(404, "USER404-0", "User Not Found"),
    REPORT_NOT_FOUND(404, "REPORT404-0", "Report Not Found"),
    INVALID_ACCESS(401, "REPORT401-0", "Invalid Access"),
    NOTICE_NOT_FOUND(404, "NOTICE404-0","Notice Not Found"),
    COMMENT_NOT_FOUND(404, "REPORT404-0","Report Not Found"),
    USER_ALREADY_EXIST(409, "USER409-0","User Already Exist"),
    SECRET_KEY_MISMATCH(403, "EMAIL403-0","Secret Key Mismatch"),
    NUMBER_NOT_FOUND(404, "NUMBER404-0","Number Not Found"),
    EMAIL_SEND_FAIL(500, "EMAIL500-0", "Email Send Failed");

    private final int status;
    private final String code;
    private final String message;
}
