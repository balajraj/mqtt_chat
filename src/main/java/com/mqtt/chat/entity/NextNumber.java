package com.mqtt.chat.entity;

public class NextNumber {

  private int sum;
  private int nextNumber;
  
  public NextNumber(int sum, int nextNumber) {
    this.sum = sum;
    this.nextNumber = nextNumber;
  }
  
  public int getSum() {
    return sum;
  }
  
  public int getNextNumber() {
    return nextNumber;
  }
}
