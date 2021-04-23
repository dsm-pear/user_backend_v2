package com.dsmpear.main.user_backend_v2.exception;

import com.dsmpear.main.user_backend_v2.exception.handler.ErrorCode;
import com.dsmpear.main.user_backend_v2.exception.handler.PearUserException;

public class ReportNotFoundException extends PearUserException {
    public ReportNotFoundException() {
        super(ErrorCode.REPORT_NOT_FOUND);
    }
}
