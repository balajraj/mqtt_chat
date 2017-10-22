
package com.mqtt.chat.service;

import com.mqtt.chat.Constants;
import com.mqtt.chat.entity.ChatMessage;
import com.mqtt.chat.repository.MessageClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMessageService {
  private static final Logger logger = LoggerFactory.getLogger (SendMessageService.class);

  private MessageClient msgRep;

  private ConfigurationService config;
  @Autowired
  private JacksonWrapperService jackson;

  private int qos = 0;
  private String clientId = null;
  private String broker = null;
  private String session = null;
  private String retained = null;

  public SendMessageService () {
    this.config = new ConfigurationService ();
    this.clientId = config.getProperty (Constants.clientid);
    this.broker = config.getProperty (Constants.broker);
    this.qos = Integer.parseInt (config.getProperty (Constants.qos));
    this.session = config.getProperty (Constants.clean);
    this.retained = config.getProperty (Constants.retained);
    this.msgRep = MessageClient.getInstance (clientId, broker, qos, session);

  }
 
  /**
   * Will send the message out to the topic mentioned
   * @param friendName name of the topic to which message will be send out
   * @param msg the actual payload
   */
  public void sendMessage (String friendName, ChatMessage msg) {

    String message = null;
    int retryCount = 1;
    try {
      message = jackson.getMapper ().writeValueAsString (msg);
      logger.info ("sending message to " + friendName + " message " + message);
      msgRep.sendMessage (friendName, message, retryCount, retained);

    } catch (JsonProcessingException e) {
      logger.error ("Failed to get json string", e);
    }
  }
}
