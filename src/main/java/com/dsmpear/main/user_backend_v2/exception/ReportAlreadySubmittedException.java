package com.dsmpear.main.user_backend_v2.exception;

import com.dsmpear.main.user_backend_v2.exception.handler.ErrorCode;
import com.dsmpear.main.user_backend_v2.exception.handler.PearUserException;

public class ReportAlreadySubmittedException extends PearUserException {
    public ReportAlreadySubmittedException() {
        super(ErrorCode.REPORT_ALREADY_SUBMITTED);
    }
}
