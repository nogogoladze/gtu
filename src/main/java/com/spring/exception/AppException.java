package com.spring.exception;

import com.spring.enums.AppErrorCode;

public class AppException extends Exception{
    private AppErrorCode appErrorCode;

    private String[] params;


    public AppException(AppErrorCode message) {
        super(String.valueOf(message));
    }
}
