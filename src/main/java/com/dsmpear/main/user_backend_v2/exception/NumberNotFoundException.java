package com.dsmpear.main.user_backend_v2.exception;

import com.dsmpear.main.user_backend_v2.exception.handler.ErrorCode;
import com.dsmpear.main.user_backend_v2.exception.handler.PearUserException;

public class NumberNotFoundException extends PearUserException {
    public NumberNotFoundException() {
        super(ErrorCode.NUMBER_NOT_FOUND);
    }
}
