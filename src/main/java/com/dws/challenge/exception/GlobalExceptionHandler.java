package com.dws.challenge.exception;


import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountAmountLessException.class)
    public ResponseEntity<ExceptionResponse> handleAccountAmountlessException(AccountAmountLessException accountAmountLessException)
    {
        ExceptionResponse exceptionResponse=new ExceptionResponse();
        exceptionResponse.setMessage(accountAmountLessException.getMessage());
        exceptionResponse.setStatusCode("500");
       return  new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatusCode.valueOf(500));
    }
}
