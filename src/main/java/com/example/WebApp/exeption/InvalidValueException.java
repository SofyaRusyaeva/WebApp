package com.example.WebApp.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidValueException extends RuntimeException {
  private final String viewName;

  public InvalidValueException(String message, String viewName) {
    super(message);
    this.viewName = viewName;
  }

  public String getViewName() {
    return viewName;
  }
}
