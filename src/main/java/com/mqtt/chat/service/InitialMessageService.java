
package com.mqtt.chat.service;

import com.mqtt.chat.entity.ChatMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitialMessageService {

  @Autowired
  private ConfigurationService config;

  @Autowired
  private SendMessageService senderService;

  public InitialMessageService () {

  }

  /**
   * This service is responsible for sending out the first message
   * @param friend is name of the topic to which message will be published
   * @param number the intial number to be played on
   */
  public void emitFirstMessage (String friend, int number) {

    String topic = config.getProperty ("topic");

    if (number < 3) {
      System.out.println ("Please enter a number greate than 3");
    } else {
      ChatMessage cmsg = new ChatMessage ();
      cmsg.setSender (topic);
      cmsg.setNumber (number);
      senderService.sendMessage (friend, cmsg);
    }
  }
}
