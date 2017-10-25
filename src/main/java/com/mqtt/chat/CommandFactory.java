
package com.mqtt.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.mqtt.chat.service.ConfigurationService;

import java.util.Optional;

@Service
public class CommandFactory {

  @Autowired
  GameCommand cmd;
  
  private static final Logger logger = LoggerFactory.getLogger (ConfigurationService.class);

  /**
   * Implements the command pattern only send command is supported for now
   * @param line has the command and args
   * @param context  spring application context
   * @return
   */
  public  Optional<ICommand> getCommand (String line) {

    String [] commandArgs = line.split (" ");
    Optional<ICommand> ret = Optional.empty ();
    if (commandArgs[0].trim ().equals (Constants.send)) {
      if (commandArgs.length != 3) {
        System.out.println (Constants.send+" <topic> <number> to start the game");
        return ret;
      }
      String friend = commandArgs[1].trim ();
      if(friend.equals (System.getProperty (Constants.topic))){
        System.out.println ("Please start the game with your friend");
        return ret;
      }
      cmd.setFriend (friend);
      cmd.setNumber (Integer.parseInt (commandArgs[2].trim ()));
      ret = Optional.of ((ICommand) cmd);

    } else {
      System.out.println ("Please enter a valid command");
    }
    return ret;
  }
}
