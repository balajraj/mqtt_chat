
package com.mqtt.chat;

import java.util.Scanner;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.mqtt.chat.service.MessageCallBackService;
import com.mqtt.chat.service.SendMessageService;

import java.util.Optional;

@Component
public class ChatApplication {
  
  private static final Logger logger = LoggerFactory.getLogger (ChatApplication.class);

  @Autowired
  private MessageCallBackService callback;
  
  @Autowired
  private CommandFactory factory;
  @Autowired
  private SendMessageService sendservice;

  public ChatApplication () {

  }

  /**
   * Initializes the call back service and also reads input from command line
   */
  public void init () {
    
    logger.debug ("inside the init");
    Scanner in = new Scanner (System.in);
    callback.subscribe ();
    while (in.hasNext ()) {
      Optional<ICommand> cmd = factory.getCommand (in.nextLine ());
      if (cmd.isPresent ()) {
        cmd.get ().execute ();
      }

    }
  }

  public static void main (String [] args) {
    ApplicationContext context = 
        new ClassPathXmlApplicationContext(Constants.applicationContext);

    ChatApplication app = context.getBean(ChatApplication.class);
    app.init();
    
  }

}
