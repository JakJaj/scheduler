package com.jj.scheduler.exceptions;

public class EntityAlreadyExistsException extends IllegalArgumentException{
    public EntityAlreadyExistsException(String errorMessage){
        super(errorMessage);
    }
}
