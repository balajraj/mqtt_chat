
package com.mqtt.chat;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.mqtt.chat.service.MessageCallBackService;

public class ChatApplicationTest {

  @InjectMocks
  ChatApplication app;

  @Mock
  CommandFactory factory;

  @Mock
  MessageCallBackService callback;

  private String input;
  
  @Mock
  GameCommand cmd;

  @Before
  public void setup () {

    initMocks (this);
    input = new String ("SEND germany/berlin/muller 10");
    ByteArrayInputStream in = new ByteArrayInputStream (input.getBytes ());
    System.setIn (in);
    Optional<ICommand> ret = Optional.of ((ICommand) cmd);
    when (factory.getCommand (input)).thenReturn (ret);

  }

  @Test
  public void testChatApp () {

    app.init ();
    verify (callback, times (1)).subscribe ();
    verify (factory, times (1)).getCommand (input);
  }

}
