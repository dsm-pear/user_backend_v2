package com.dsmpear.main.user_backend_v2.exception;

import com.dsmpear.main.user_backend_v2.exception.handler.ErrorCode;
import com.dsmpear.main.user_backend_v2.exception.handler.PearUserException;

public class UserNotFoundException extends PearUserException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
