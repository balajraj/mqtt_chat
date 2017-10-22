package com.mqtt.chat.service;

import static org.mockito.Mockito.when;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.mqtt.chat.Constants;
import com.mqtt.chat.entity.ChatMessage;

public class InitialMessageServiceTest {
  
  @InjectMocks
  private InitialMessageService service ;
  
  @Mock
  private SendMessageService sendService;
  
  @Mock
  private ConfigurationService config;
  
  @Before 
  public void setUp() {
    initMocks(this);
    when(config.getProperty (Constants.topic)).thenReturn("user1");    
  }
  
  @Test
  public void testInitialMessage() {
    
    service.emitFirstMessage("user1", 2);
    service.emitFirstMessage ("user2", 20);
    verify(sendService, times(1)).sendMessage(any(String.class),any(ChatMessage.class));
    
  }


}
