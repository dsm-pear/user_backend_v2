package com.dsmpear.main.user_backend_v2.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_TOKEN(400, "AUTH400-0", "Invalid Token"),
  
    USER_NOT_FOUND(404, "USER404-0", "User Not Found"),
    USER_CANNOT_ACCESS(401, "USER401-0", "User Cannot Access"),
    USER_ALREADY_EXIST(409, "USER409-0","User Already Exist"),
  
    REPORT_NOT_FOUND(404, "REPORT404-0", "Report Not Found"),
    INVALID_ACCESS(401, "REPORT401-0", "Invalid Access"),
    COMMENT_NOT_FOUND(404, "REPORT404-0","Report Not Found"),
  
    NOTICE_NOT_FOUND(404, "NOTICE404-0","Notice Not Found"),
    
    EMAIL_SEND_FAIL(500, "EMAIL500-0", "Email Send Failed"),
    SECRET_KEY_MISMATCH(403, "EMAIL403-0","Secret Key Mismatch"),
  
    NUMBER_NOT_FOUND(404, "NUMBER404-0","Number Not Found");

    private final int status;
    private final String code;
    private final String message;
}
