package com.mqtt.chat.service;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import com.mqtt.chat.Constants;
import com.mqtt.chat.entity.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit test for NextNumber service
 */
public class GameOf3ServiceTest     
{
    
    
    @InjectMocks
    private GameOf3Service service ;
    
    @Mock
    private SendMessageService sendService;
    
    @Mock
    private ConfigurationService config;
    
    @Before 
    public void setUp() {
      initMocks(this);
      when(config.getProperty (Constants.clientid)).thenReturn("12345");
      when(config.getProperty (Constants.topic)).thenReturn("user1");
      
    }
    
    @Test
    public void testNextNumber() throws InvalidInputException
    {
        NextNumber num = service.getNextNumber (56);
        assertTrue(num.getSum()==1);
        assertTrue(num.getNextNumber ()==19);
        num = service.getNextNumber (19);
        assertTrue(num.getSum() == -1);
        assertTrue(num.getNextNumber() == 6);
        num = service.getNextNumber(6);
        assertTrue(num.getSum() == 0 );
        assertTrue(num.getNextNumber() == 2);
        ChatMessage cmsg = new ChatMessage();
        cmsg.setNumber (10);
        cmsg.setSender ("user2");
        String result = service.checkForWinner (cmsg);
        //System.out.println(result);
        assertTrue(result.equals("[user1 playing with user2]\n[CURRENT NUM:10 NEXT NUM:3 ADDING: -1]\n"));
        cmsg.setNumber(4);
        result = service.checkForWinner (cmsg);
        //System.out.println(result);
        assertTrue(result.equals("[user1 playing with user2]\n[CURRENT NUM:4 NEXT NUM:1 ADDING: -1]\n[user1 is the WINNER]"));
    }
    
    @Test(expected = InvalidInputException.class)
    public void testInputInvalidException() throws InvalidInputException{
      NextNumber num = service.getNextNumber (0);
    }

    

    
}
