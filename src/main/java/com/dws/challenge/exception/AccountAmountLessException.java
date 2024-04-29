package com.dws.challenge.exception;

public class AccountAmountLessException extends RuntimeException{

    public AccountAmountLessException(String message){
        super(message);
    }

}
