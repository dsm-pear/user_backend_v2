package com.dsmpear.main.user_backend_v2.exception.handler;

import lombok.Getter;

@Getter
public class PearUserException extends RuntimeException {

    private final ErrorCode errorCode;

    public PearUserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
