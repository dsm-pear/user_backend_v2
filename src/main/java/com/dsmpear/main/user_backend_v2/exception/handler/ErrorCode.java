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
    REPORT_ALREADY_SUBMITTED(400,"REPORT400-1", "Report Already Submitted"),
  
    NOTICE_NOT_FOUND(404, "NOTICE404-0","Notice Not Found"),
    
    EMAIL_SEND_FAIL(500, "EMAIL500-0", "Email Send Failed"),
    SECRET_KEY_MISMATCH(403, "EMAIL403-0","Secret Key Mismatch"),
  
    NUMBER_NOT_FOUND(404, "NUMBER404-0","Number Not Found"),

    MEMBER_NOT_FOUND(404, "MEMBER404-0", "Member Not Found"),
    USER_NOT_MEMBER(403, "MEMBER403-0","User Not Member of this report"),
    USER_EQUAL_MEMBER(400,"MEMBER400-0", "User Equals Member"),
    USER_ALREADY_MEMBER(409, "MEMBER409-0", "User already Include this report as Member");

    private final int status;
    private final String code;
    private final String message;
}
