package com.mqtt.chat.entity;

/**
 * The messagepayload encapsulates the chat messages that are exchanged between
 * one client to the other client right now only one message type namey gameof3 is
 * supported in future this can be extended to support other messagetypes as well
 *
 *
 */
public class MessagePayload {
  
  private String commandType;
  private String payload;
  
  public MessagePayload() {
    
  }
  
  public void setCommandType(String commandType) {
    this.commandType = commandType;
  }
  
  public void setPayload(String payload) {
    this.payload = payload;
  }
  
  public String getPayload() {
    return payload;
  }
  
  public String getCommandType() {
    return commandType;
  }

}
