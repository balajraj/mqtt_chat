
package com.mqtt.chat;

import java.util.Scanner;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;

import com.mqtt.chat.service.ConfigurationService;
import com.mqtt.chat.service.MessageCallBackService;
import com.mqtt.chat.service.SendMessageService;

import java.util.Optional;

@Service
public class ChatApplication {
  private MessageCallBackService callback;
  private SendMessageService sendservice;

  public ChatApplication () {

  }

  /**
   * Initializes the call back service and also reads input from command line
   */
  public void init () {

    LogAppender.init ();
    Scanner in = new Scanner (System.in);
    ApplicationContext context = new ClassPathXmlApplicationContext (Constants.applicationContext);
    ConfigurationService config = context.getBean (ConfigurationService.class);
    callback = context.getBean (MessageCallBackService.class);
    callback.subscribe (config);
    while (in.hasNext ()) {
      Optional<ICommand> cmd = CommandFactory.getCommand (in.nextLine (), context);
      if (cmd.isPresent ()) {
        cmd.get ().execute ();
      }

    }
  }

  public static void main (String [] args) {

    ChatApplication app = new ChatApplication ();
    app.init ();
  }

}
