
package com.mqtt.chat;

import com.mqtt.chat.service.InitialMessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameCommand implements ICommand {

  private String friend = null;
  private int number = 0;
  @Autowired
  private InitialMessageService initService;

  public GameCommand () {
  }

  public void setFriend (String friend) {

    this.friend = friend;
  }

  public void setNumber (int number) {

    this.number = number;
  }

  public void execute () {

    initService.emitFirstMessage (friend, number);
  }

}
