package com.dsmpear.main.user_backend_v2.exception;

import com.dsmpear.main.user_backend_v2.exception.handler.ErrorCode;
import com.dsmpear.main.user_backend_v2.exception.handler.PearUserException;

public class UserNotMemberException extends PearUserException {
    public UserNotMemberException() {
        super(ErrorCode.USER_NOT_MEMBER);
    }
}
