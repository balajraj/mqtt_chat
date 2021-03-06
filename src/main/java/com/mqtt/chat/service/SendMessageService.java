
package com.mqtt.chat.service;

import com.mqtt.chat.Constants;
import com.mqtt.chat.entity.ChatMessage;
import com.mqtt.chat.entity.MessagePayload;
import com.mqtt.chat.repository.MessageClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMessageService {
  private static final Logger logger = LoggerFactory.getLogger (SendMessageService.class);

  @Autowired
  private MessageClient msgRep;

  @Autowired
  private JacksonWrapperService jackson;

  private int qos = 0;
  private String clientId = null;
  private String broker = null;
  private String session = null;
  private String retained = null;
  private int retryCount = 1;

  public SendMessageService () {
  }

  /**
   * Will send the message out to the topic mentioned. For fault tolerance retry of 1 is supported for now.
   * 
   * @param friendName
   *          name of the topic to which message will be send out
   * @param msg
   *          the actual payload
   */
  public void sendMessage (String friendName, ChatMessage msg) {

    String message = null;
    
    try {
      MessagePayload payload = new MessagePayload ();
      payload.setPayload (jackson.getMapper ().writeValueAsString (msg));
      payload.setCommandType (Constants.gameof3);
      message = jackson.getMapper ().writeValueAsString (payload);
      logger.info ("sending message to " + friendName + " message " + message);
      msgRep.sendMessage (friendName, message, retryCount, retained);

    } catch (JsonProcessingException e) {
      logger.error ("Failed to get json string", e);
    }
  }
}
