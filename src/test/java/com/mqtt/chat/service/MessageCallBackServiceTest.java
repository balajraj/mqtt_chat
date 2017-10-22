package com.mqtt.chat.service;

import static org.mockito.Mockito.when;

import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mqtt.chat.Constants;
import com.mqtt.chat.entity.ChatMessage;
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
  
  @Before
  public void setup() throws Exception, JsonMappingException, IOException {
    initMocks(this);
    when(config.getProperty (Constants.topic)).thenReturn("user1");    
    when(config.getProperty (Constants.qos)).thenReturn("2");    
    when(config.getProperty (Constants.clean)).thenReturn("false");    
    when(config.getProperty (Constants.broker)).thenReturn("tcp://localhost:1333");    
    when(wrapper.getMapper ()).thenReturn (mapper);
    when(mapper.readValue(any(String.class),any(Class.class))).thenReturn(new ChatMessage());
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testSubscribe() {
    service.subscribe (config);
  }
  
  @Test 
  public void callback() throws Exception {
    
    MqttMessage msg = new MqttMessage();
    String hello = new String("hello");
    msg.setPayload (hello.getBytes ());
    service.processMessage (hello);
    verify(mapper,times(1)).readValue (any(String.class), any(Class.class));
  }

}
