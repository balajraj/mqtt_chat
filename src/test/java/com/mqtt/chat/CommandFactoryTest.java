package com.mqtt.chat;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
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
    System.setProperty (Constants.topic,"user1");
    
  }
  
  @Test
  public void testCommand() {
    Optional<ICommand> cmd = factory.getCommand("SEND muller 56");
    assertTrue(cmd.isPresent ());
    
    cmd = factory.getCommand("SEND muller");
    assertTrue(!cmd.isPresent ());
    
    cmd = factory.getCommand("SEND muller 10000000000");
    assertTrue(!cmd.isPresent ());
    
    cmd = factory.getCommand("SEND muller 45465464x");
    assertTrue(!cmd.isPresent ());
    
    cmd = factory.getCommand("SEND muller -4");
    assertTrue(!cmd.isPresent ());
    
    cmd = factory.getCommand("SEND user1 56");
    assertTrue(!cmd.isPresent ());
    
    
  }
  
  
}