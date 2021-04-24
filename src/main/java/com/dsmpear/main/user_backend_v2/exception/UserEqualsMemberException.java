package com.dsmpear.main.user_backend_v2.exception;

import com.dsmpear.main.user_backend_v2.exception.handler.ErrorCode;
import com.dsmpear.main.user_backend_v2.exception.handler.PearUserException;

public class UserEqualsMemberException extends PearUserException {
    public UserEqualsMemberException() {
        super(ErrorCode.USER_EQUAL_MEMBER);
    }
}
