
package com.mqtt.chat.repository;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

/**
 * The wrapper makes the unit test for the messageClient possible so the external network calls like
 * connect can be mocked
 */
@Service
public class MqttClientWrapper {

  private MqttClient mqclient;

  public MqttClientWrapper () {

  }

  public void init (String broker, String clientId, MemoryPersistence persistence)
      throws MqttException {

    this.mqclient = new MqttClient (broker, clientId, persistence);

  }

  public void connect (MqttConnectOptions connOpts) throws MqttSecurityException, MqttException {

    this.mqclient.connect (connOpts);

  }

  public MqttClient getClient () {

    return mqclient;
  }

}
