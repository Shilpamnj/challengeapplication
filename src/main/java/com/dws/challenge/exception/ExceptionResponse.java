package com.dws.challenge.exception;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionResponse {

    private String message;
    private String statusCode;
    private LocalDateTime timeStamp = LocalDateTime.now();
}
