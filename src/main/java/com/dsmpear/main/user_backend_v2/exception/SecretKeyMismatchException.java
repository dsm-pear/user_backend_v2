package com.dsmpear.main.user_backend_v2.exception;

import com.dsmpear.main.user_backend_v2.exception.handler.ErrorCode;
import com.dsmpear.main.user_backend_v2.exception.handler.PearUserException;

public class SecretKeyMismatchException extends PearUserException {
    public SecretKeyMismatchException() {
        super(ErrorCode.SECRET_KEY_MISMATCH);
    }
}
