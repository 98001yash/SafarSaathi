package com.company.SafarSaathi.chat_service.exceptions;

public class BadRequestException extends RuntimeException{

  public BadRequestException(String message){
      super(message);
  }
}
