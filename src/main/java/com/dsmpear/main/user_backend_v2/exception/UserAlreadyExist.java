package com.dsmpear.main.user_backend_v2.exception;

import com.dsmpear.main.user_backend_v2.exception.handler.ErrorCode;
import com.dsmpear.main.user_backend_v2.exception.handler.PearUserException;

public class UserAlreadyExist extends PearUserException {
    public UserAlreadyExist() {
        super(ErrorCode.USER_ALREADY_EXIST);
    }
}
