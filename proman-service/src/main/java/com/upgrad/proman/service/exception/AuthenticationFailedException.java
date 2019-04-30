package com.upgrad.proman.service.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintStream;
import java.io.PrintWriter;

public class AuthenticationFailedException extends Exception {

    private String code;
    private String message;

    public AuthenticationFailedException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
