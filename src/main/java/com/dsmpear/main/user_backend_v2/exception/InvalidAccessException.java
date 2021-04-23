package com.dsmpear.main.user_backend_v2.exception;

import com.dsmpear.main.user_backend_v2.exception.handler.ErrorCode;
import com.dsmpear.main.user_backend_v2.exception.handler.PearUserException;

public class InvalidAccessException extends PearUserException {
    public InvalidAccessException() {
        super(ErrorCode.INVALID_ACCESS);
    }
}
