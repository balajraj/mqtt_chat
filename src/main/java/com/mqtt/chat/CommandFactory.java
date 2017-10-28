
package com.mqtt.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

/**
 * The class checks the input condition using the chain of responsibility if all the conditions pass
 * then the command is generated to be processed downstream.
 */
@Service
public class CommandFactory {

  @Autowired
  GameCommand cmd;

  private static final Logger logger = LoggerFactory.getLogger (CommandFactory.class);

  /**
   * Implements the command pattern, only send command is supported for now
   * 
   * @param line
   *          has the command and args
   * @param context
   *          spring application context
   * @return
   */
  public Optional<ICommand> getCommand (String line) {

    String [] commandArgs = line.split (" ");
    Optional<ICommand> ret = Optional.empty ();
    if (!checkInputLength (commandArgs)) {
      String friend = commandArgs[1].trim ();
      String number = commandArgs[2].trim ();  
      cmd.setFriend (friend);
      cmd.setNumber (Integer.parseInt (number));
      ret = Optional.of ((ICommand) cmd);
    }
    return ret;
  }

  /**
   * Check the command name is SEND
   * @param commandName
   * @param friend
   * @param number
   * @return
   */
  public boolean checkCommandName (String commandName, String friend, String number) {

    boolean failed = false;
    if (!commandName.equals (Constants.send)) {
      failed = true;
      System.out.println ("Please enter a valid command");

    } else {
      failed = checkGameInitiationWithUser (friend, number);
    }
    return failed;
  }

  /**
   * Check for input length to be 3
   * @param commandArgs
   * @return
   */
  public boolean checkInputLength (String[] commandArgs) {
   
    boolean failed = false;
    if (commandArgs.length != 3) {
      System.out.println (Constants.send + " <topic> <number> to start the game");
      failed = true;
    } else {
      String friend = commandArgs[1].trim ();
      String commandName = commandArgs[0].trim ();
      String number = commandArgs[2].trim ();
      
      failed = checkCommandName (commandName,friend, number);
    }
    return failed;
  }

  /**
   * Do not allow the user to start game with oneself
   * @param friend
   * @param number
   * @return
   */
  public boolean checkGameInitiationWithUser (String friend, String number) {

    boolean failed = false;
    if (friend.equals (System.getProperty (Constants.topic))) {
      System.out.println ("Please start the game with your friend");
      failed = true;
    } else {
      failed = checkIntegerBounds (number);
    }
    return failed;
  }

  /**
   * If the number is really an integer
   * @param number
   * @return
   */
  public boolean checkIntegerBounds (String number) {

    boolean failed = false;
    Integer num = 0;
    try {
      num = Integer.parseInt (number);
      failed = checkIntegerValue (num);
    } catch (Exception ex) {
      System.out.println ("Please enter a integer");
      logger.error ("Failed to parse the integer", ex);
      failed = true;
    }
    return failed;
  }

  /**
   * Check if number is greater than 3
   * @param number
   * @return
   */
  public boolean checkIntegerValue (int number) {

    boolean failed = false;
    if (number < 3) {
      System.out.println ("Please enter a number greate than 3");
      failed = true;
    }
    return failed;
  }

}
