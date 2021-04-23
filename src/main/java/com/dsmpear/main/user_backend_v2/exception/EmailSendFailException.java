package com.dsmpear.main.user_backend_v2.exception;

import com.dsmpear.main.user_backend_v2.exception.handler.ErrorCode;
import com.dsmpear.main.user_backend_v2.exception.handler.PearUserException;

public class EmailSendFailException extends PearUserException {
    public EmailSendFailException() {
        super(ErrorCode.EMAIL_SEND_FAIL);
    }
}
