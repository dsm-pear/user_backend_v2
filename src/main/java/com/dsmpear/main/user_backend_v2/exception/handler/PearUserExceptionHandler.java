package com.dsmpear.main.user_backend_v2.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PearUserExceptionHandler {

    @ExceptionHandler(PearUserException.class)
    protected ResponseEntity<ErrorResponse> handlePearException(final PearUserException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(new ErrorResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage()),
                HttpStatus.valueOf(errorCode.getStatus()));
    }

}
