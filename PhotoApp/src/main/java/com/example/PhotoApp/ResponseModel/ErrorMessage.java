package com.example.PhotoApp.ResponseModel;

import java.util.Date;


public class ErrorMessage {


  private Date timestamp;
  private String message;

  public ErrorMessage(Date timestamp, String message) {
    this.timestamp = timestamp;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {

  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
}
