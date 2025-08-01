package com.company.SafarSaathi.auth_service.exceptions;

public class BadRequestException extends RuntimeException{

  public BadRequestException(String message){
      super(message);
  }
}
