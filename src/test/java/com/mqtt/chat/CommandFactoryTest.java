package com.mqtt.chat;

import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


 public class CommandFactoryTest {
   
  @InjectMocks
  CommandFactory factory;
  
  
  @Mock
  GameCommand cmd;
  
  @Before 
  public void setup() {
    initMocks(this);
    
  }
  
  @Test
  public void testCommand() {
    Optional<ICommand> cmd = factory.getCommand("SEND muller 56");
    assertTrue(cmd.isPresent ());
    
  }
  
  
}