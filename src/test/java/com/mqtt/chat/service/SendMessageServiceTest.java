package com.mqtt.chat.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mqtt.chat.entity.ChatMessage;
import com.mqtt.chat.repository.MessageClient;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.anyInt;


public class SendMessageServiceTest {

  @InjectMocks
  private SendMessageService service ;
  
  
  @Mock
  private JacksonWrapperService jackson;
  
  @Mock
  private ObjectMapper mapper;
  
  @Mock
  private MessageClient msgclient;
  
  @Before
  public void setup() throws Exception {
    System.setProperty("configfile","src/main/resources/config.properties");
    initMocks(this);
    when(jackson.getMapper ()).thenReturn (mapper);
    when(mapper.writeValueAsString(any(Object.class))).thenReturn("hello");
    Mockito.doNothing ().when(msgclient).sendMessage( any(String.class), any(String.class), anyInt(), any(String.class));
  }
  
  @Test
  public void testSendMsg() {
    ChatMessage cmsg = new ChatMessage();
    service.sendMessage ("user1", cmsg);
    verify(msgclient,times(1)).sendMessage(any(String.class), any(String.class), anyInt(), any(String.class));
    
  }
  
}
