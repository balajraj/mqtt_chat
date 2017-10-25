
package com.mqtt.chat.repository;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.springframework.stereotype.Service;

@Service
public class MqttTopicWrapper {

  private MqttTopic mtopic;

  /**
   * Wrapper takes care of dealing with external network calls and keeps messagecleint clean for unit tests
   */
  public MqttTopicWrapper () {

  }

  public MqttDeliveryToken sendMessage (MqttClient client, MqttMessage message, String topic)
      throws MqttPersistenceException,
      MqttException {

    mtopic = client.getTopic (topic);
    MqttDeliveryToken token = mtopic.publish (message);
    return token;
  }

}
