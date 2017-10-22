
package com.mqtt.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.mqtt.chat.service.ConfigurationService;

import java.util.Optional;

public class CommandFactory {

  private static final Logger logger = LoggerFactory.getLogger (ConfigurationService.class);

  /**
   * Implements the command pattern only send command is supported for now
   * @param line has the command and args
   * @param context  spring application context
   * @return
   */
  public static Optional<ICommand> getCommand (String line, ApplicationContext context) {

    String [] commandArgs = line.split (" ");
    Optional<ICommand> ret = Optional.empty ();
    if (commandArgs[0].trim ().equals ("SEND")) {
      if (commandArgs.length != 3) {
        System.out.println ("SEND <topic> <number> to start the game");
        return ret;
      }
      GameCommand cmd = context.getBean (GameCommand.class);
      cmd.setFriend (commandArgs[1].trim ());
      cmd.setNumber (Integer.parseInt (commandArgs[2].trim ()));
      ret = Optional.of ((ICommand) cmd);

    } else {
      System.out.println ("Please enter a valid command");
    }
    return ret;
  }
}
