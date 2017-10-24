
package com.mqtt.chat.repository;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * The MessageClient implements singleton since mqtt client works only with one instance of client.
 */
@Scope (value = "singleton")
@Service
public class MessageClient {
  private MqttClient mqclient;
  private static final Logger logger = LoggerFactory.getLogger (MessageClient.class);
  private static Object mutex = new Object ();
  private int qos = 0;
  private String clientId = null;
  private String broker = null;
  private String session = null;

  public MessageClient () {

  }

  /**
   * The sendMessage will publish the message to the give topic
   * 
   * @param topic
   *          to which the message is published
   * @param msg
   *          The payload to be send out
   * @param retryCount
   *          only one retry is supported now
   * @param retained
   *          if set to true messages are retained even if no one to listen to them
   */
  public void sendMessage (String topic, String msg, int retryCount, String retained) {

    try {
      logger.info ("Publishing message: " + msg + " " + qos);
      MqttMessage message = new MqttMessage (msg.getBytes ());
      MqttTopic mtopic = this.mqclient.getTopic (topic);
      message.setQos (qos);
      message.setRetained (Boolean.valueOf (retained));
      MqttDeliveryToken token = null;
      token = mtopic.publish (message);
      token.waitForCompletion ();
      logger.info ("Message published");

    } catch (MqttException ex) {

      ex.printStackTrace ();
      if (retryCount != 0) {
        reconnect (clientId, broker, qos, session);
        sendMessage (msg, topic, 0, retained);
      }
    }
  }

  /**
   * Initializes the mqtt client
   * 
   * @param clientId
   *          it has to unique for each client connected to mqtt broker
   * @param broker
   *          the mqtt broker url
   * @param qos
   *          quality service if set to 2 has exactly once delivery guarantee
   * @param session
   *          if set to false becomes stateful
   */
  public void init (String clientId, String broker, int qos, String session) {

    this.qos = qos;
    this.broker = broker;
    this.clientId = clientId;
    logger.debug ("msgclient is null initializing ");
    try {
      MemoryPersistence persistence = new MemoryPersistence ();

      this.mqclient = new MqttClient (broker, clientId, persistence);
      MqttConnectOptions connOpts = new MqttConnectOptions ();
      connOpts.setCleanSession (Boolean.valueOf (session));
      logger.info ("Connecting to broker: " + broker);
      this.mqclient.connect (connOpts);
      logger.info ("Connected");

    } catch (MqttException me) {
      logger.error ("reason " + me.getReasonCode (), me);
      logger.error ("msg " + me.getMessage ());
      logger.error ("loc " + me.getLocalizedMessage ());
      logger.error ("cause " + me.getCause ());
      logger.error ("excep " + me);
    }

  }

  public MessageClient (String clientId, String broker, int qos, String session) {
    init (clientId, broker, qos, session);
  }

  public void reconnect (String clientId, String broker, int qos, String session) {

    init (clientId, broker, qos, session);
  }

  public MqttClient getClient () {

    return mqclient;
  }

}
