
package com.mqtt.chat.service;

import com.mqtt.chat.entity.ChatMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitialMessageService {

  private static final Logger logger = LoggerFactory.getLogger (InitialMessageService.class);

  @Autowired
  private ConfigurationService config;

  @Autowired
  private SendMessageService senderService;

  public InitialMessageService () {

  }

  /**
   * This service is responsible for sending out the first message
   * 
   * @param friend
   *          is name of the topic to which message will be published
   * @param number
   *          the initial number to be played on
   */
  public void emitFirstMessage (String friend, int number) {

    logger.debug ("sending the first message");
    String topic = config.getProperty ("topic");

    ChatMessage cmsg = new ChatMessage ();
    cmsg.setSender (topic);
    cmsg.setNumber (number);
    senderService.sendMessage (friend, cmsg);

  }
}
