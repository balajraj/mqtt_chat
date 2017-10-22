package com.mqtt.chat.entity;

public class ChatMessage {
  
  
  private String sender;
  private int number;
  
  public ChatMessage() {
  }
  
  public String getSender() {
    return sender;
  }
  
  public int getNumber() {
    return number;
  }
  
  public void setSender(String sender) {
    this.sender = sender;
  }
  
  public void setNumber(int number) {
    this.number = number;
  }
  
  
}
