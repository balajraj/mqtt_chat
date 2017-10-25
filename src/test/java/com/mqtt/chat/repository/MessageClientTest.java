
package com.mqtt.chat.repository;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class MessageClientTest {

  @InjectMocks
  MessageClient client;

  @Mock
  MqttClientWrapper mclient;

  @Mock
  MqttTopicWrapper topic;

  @Mock
  MqttDeliveryToken token;

  @Before
  public void setup () throws MqttPersistenceException, MqttException {

    initMocks (this);
    when (topic.sendMessage (any (MqttClient.class), any (MqttMessage.class), any(String.class)))
      .thenReturn (token);

  }

  @Test
  public void testClient () throws MqttSecurityException, MqttException {

    client.init ("1234", "tcp://locahost:1345", 2, "false");
    verify (mclient, times (1)).connect (any (MqttConnectOptions.class));
    client.sendMessage ("user1", "test", 1, "true");
    verify (token, times (1)).waitForCompletion ();
    verify (topic, times (1))
      .sendMessage (any (MqttClient.class), any (MqttMessage.class), any(String.class));

  }

}
