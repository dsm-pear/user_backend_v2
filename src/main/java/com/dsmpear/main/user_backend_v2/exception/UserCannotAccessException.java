package com.dsmpear.main.user_backend_v2.exception;

import com.dsmpear.main.user_backend_v2.exception.handler.ErrorCode;
import com.dsmpear.main.user_backend_v2.exception.handler.PearUserException;

public class UserCannotAccessException extends PearUserException {
    public UserCannotAccessException() {
        super(ErrorCode.USER_CANNOT_ACCESS);
    }
}
