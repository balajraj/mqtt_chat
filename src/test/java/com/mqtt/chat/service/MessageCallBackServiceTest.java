package com.mqtt.chat.service;

import static org.mockito.Mockito.when;

import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mqtt.chat.Constants;
import com.mqtt.chat.entity.ChatMessage;
import com.mqtt.chat.entity.MessagePayload;
import com.mqtt.chat.repository.MessageClient;

public class MessageCallBackServiceTest {
  
  @InjectMocks
  private MessageCallBackService service ;
  
  @Mock
  private GameOf3Service gameService;
  
  @Mock
  JacksonWrapperService wrapper;
  
  @Mock
  MessageClient client;
  
  @Mock
  ObjectMapper mapper;
  
  @Mock
  ConfigurationService config;
  
  @Mock
  MqttClient mclient;
  
  private MessagePayload payload;
  
  private ChatMessage csmg = new ChatMessage();
  private String message;
  
  @Before
  public void setup() throws Exception, JsonMappingException, IOException {
    System.setProperty(Constants.qos,"2");
    System.setProperty (Constants.topic, "user1");
    System.setProperty (Constants.broker, "tcp://localhost:1333");
    System.setProperty (Constants.clientid, "1343435");
    System.setProperty (Constants.clean, "false");
    
    initMocks(this);
    when(wrapper.getMapper ()).thenReturn (mapper);
    when(client.getClient()).thenReturn (mclient);
    payload = new MessagePayload();
    payload.setCommandType (Constants.gameof3);
    csmg.setNumber (3);
    csmg.setSender ("user1");
    ObjectMapper omap = new ObjectMapper();
    message = omap.writeValueAsString (payload);
    
    payload.setPayload (omap.writeValueAsString (csmg));
    when(mapper.readValue(any(String.class),any(Class.class))).thenReturn(payload);
    
    }
  
  @Test
  public void testSubscribe() {
    service.subscribe ();
    verify(client,times(1)).init ("1343435", "tcp://localhost:1333", 2, "false");
  }
  
  @Test 
  public void callback() throws Exception {
    service.processMessage (message);
    verify(mapper,times(2)).readValue (any(String.class), any(Class.class));
  }

}
