package com.company.SafarSaathi.companion_service.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }
}
