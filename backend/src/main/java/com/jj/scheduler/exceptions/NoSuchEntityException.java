package com.jj.scheduler.exceptions;

public class NoSuchEntityException extends IllegalArgumentException{
    public NoSuchEntityException(String errorMessage){
        super(errorMessage);
    }
}
