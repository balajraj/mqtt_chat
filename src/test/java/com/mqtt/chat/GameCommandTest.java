package com.mqtt.chat;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.mqtt.chat.service.InitialMessageService;

public class GameCommandTest {
  
  @InjectMocks
  private GameCommand cmd;
  
  @Mock
  InitialMessageService service;
  
  @Before
  public void setup() {
    initMocks(this); 
    
  }
  
  @Test
  public void testGameCmd() {
    cmd.execute();
    verify(service,times(1)).emitFirstMessage (any(String.class), anyInt());
    
  }

}
