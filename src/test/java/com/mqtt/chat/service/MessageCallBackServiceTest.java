package com.mqtt.chat.service;

import static org.mockito.Mockito.when;

import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mqtt.chat.Constants;
import com.mqtt.chat.entity.ChatMessage;
import com.mqtt.chat.entity.MessagePayload;
import com.mqtt.chat.repository.MessageClient;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:**/"+Constants.applicationContext})
//@TestPropertySource(properties = {
//    "qos=2",
//})
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
    //when(config.getProperty (Constants.topic)).thenReturn("user1");    
    //when(config.getProperty (Constants.qos)).thenReturn("2");    
    //when(config.getProperty (Constants.clean)).thenReturn("false");    
    //when(config.getProperty (Constants.broker)).thenReturn("tcp://localhost:1333");    
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
    
    //when(mapper.readValue(any(String.class),MessagePayload.class)).thenReturn(payload);
    //when(mapper.readValue (any(String.class), ChatMessage.class)).thenReturn (csmg);
;  }
  
  @Test
  public void testSubscribe() {
    service.subscribe ();
    verify(client,times(1)).init ("1343435", "tcp://localhost:1333", 2, "false");
  }
  
  @Test 
  public void callback() throws Exception {
    //ObjectMapper omap = new ObjectMapper();
    //MqttMessage msg = new MqttMessage();
    //ChatMessage csmg = new ChatMessage();
   // MessagePayload payload = new MessagePayload();
    //payload.setCommandType (Constants.gameof3);
    //payload.setPayload (omap.writeValueAsString (csmg));
    //String message = omap.writeValueAsString (payload);
    //msg.setPayload (message.getBytes ());
    service.processMessage (message);
    verify(mapper,times(2)).readValue (any(String.class), any(Class.class));
  }

}
