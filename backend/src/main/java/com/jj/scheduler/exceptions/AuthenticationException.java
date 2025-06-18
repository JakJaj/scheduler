package com.jj.scheduler.exceptions;

public class AuthenticationException extends IllegalArgumentException{
    public AuthenticationException(String errorMessage){
        super(errorMessage);
    }
}
