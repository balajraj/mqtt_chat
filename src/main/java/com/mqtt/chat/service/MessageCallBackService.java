
package com.mqtt.chat.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttException;
import com.mqtt.chat.Constants;
import com.mqtt.chat.entity.ChatMessage;
import com.mqtt.chat.repository.MessageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageCallBackService implements MqttCallback {

  @Autowired
  private GameOf3Service gameservice;
  @Autowired
  private JacksonWrapperService jackson;

  private static final Logger logger = LoggerFactory.getLogger (MessageCallBackService.class);
  private String clientId = null;
  private String broker = null;
  private int qos = 1;
  private String session = null;
  
  private MessageClient client;

  public MessageCallBackService () {
  }

  public void subscribe (ConfigurationService config) {

    try {
      clientId = config.getProperty (Constants.clientid);
      String topic = config.getProperty (Constants.topic);
      logger.info ("subscribing to " + topic);
      broker = config.getProperty (Constants.broker);
      qos = Integer.parseInt (config.getProperty (Constants.qos));
      session = config.getProperty (Constants.clean);
      client = MessageClient.getInstance (clientId, broker, qos, session);
      client.getClient ().setCallback (this);
      client.getClient ().subscribe (topic.trim (), qos);

    } catch (MqttException ex) {
      logger.error ("failed to subscribe ", ex);
    }
  }

  public void connectionLost (Throwable exception) {

    logger.error ("ConnectionList attempt reconnect ", exception);

    client.reconnect (clientId, broker, qos, session);
  }

  public void deliveryComplete (IMqttDeliveryToken token) {

    logger.info ("Delivery of message complete " + token);

  }

  public void processMessage (final String message) {

    try {
      ChatMessage cmsg = jackson.getMapper ().readValue (message, ChatMessage.class);
      logger.info ("Message received from sender: " + cmsg.getSender ());
      String result = gameservice.checkForWinner (cmsg);
      System.out.println (result);

    } catch (Exception ex) {
      logger.error ("failed to parse input json ",ex);
    }

  }

  /**
   * It really important to process the message arrived in seperate thread
   * since another message is produced to the client. Processing the message
   * in the same subscriber thread can lead to deadlock
   */
  public void messageArrived (String topic, MqttMessage mqttmsg) throws Exception {

    logger.debug ("message arrived");
    ExecutorService executor = Executors.newSingleThreadExecutor ();
    final String message = new String (mqttmsg.getPayload ());
    executor.submit ( () -> {
      processMessage (message);
    });

  }

}
