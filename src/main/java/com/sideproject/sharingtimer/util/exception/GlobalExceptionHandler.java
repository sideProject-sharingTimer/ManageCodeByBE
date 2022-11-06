package com.sideproject.sharingtimer.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@SuppressWarnings("unchecked")
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { CustomException.class })
    public ResponseEntity<Object> handleApiRequestException(CustomException ex) {
        HttpStatus status = ex.getErrorCode().getStatus();
        String errCode = ex.getErrorCode().getErrorCode();
        String errMSG = ex.getErrorCode().getErrorMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(status);
        exceptionResponse.setErrorCode(errCode);
        exceptionResponse.setErrorMessage(errMSG);

        System.out.println("ERR :" + status + " , " + errCode + " , " + errMSG);

        return new ResponseEntity(
                exceptionResponse,
                ex.getErrorCode().getStatus()
        );
    }
}