
package com.mqtt.chat.service;

import com.mqtt.chat.Constants;
import com.mqtt.chat.entity.ChatMessage;
import com.mqtt.chat.entity.NextNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 * The class holds the domain knowledge of the application
 * domain driver design is followed in this application code. 
 */
@Service
public class GameOf3Service {

  @Autowired
  private SendMessageService sendService;
  @Autowired
  private ConfigurationService config;

  @Autowired
  private JacksonWrapperService jackson;

  public GameOf3Service () {
  }

  /**
   * If 1 is reached winner is printed else the next message is send out
   * @param currentMsg has the information on topic and current number
   * @return will return the string to be printed in console
   * @throws InvalidInputException
   */
  public String checkForWinner (ChatMessage currentMsg) throws InvalidInputException {

    String topic = config.getProperty (Constants.topic);
    int number = currentMsg.getNumber ();
    StringBuffer buf = new StringBuffer ();
    NextNumber num = getNextNumber (number);
    buf.append (
      "["+topic+Constants.playing+currentMsg.getSender()+"]\n"+
      "["+Constants.current_num + number + " "+Constants.next_num + 
       num.getNextNumber () + " "+Constants.adding+" " + num.getSum ()
          + "]\n");
    if (num.getNextNumber () == 1) {
      buf.append ("[" + topic + Constants.winner+"]");
    } else {
      ChatMessage newMsg = new ChatMessage ();
      newMsg.setNumber (num.getNextNumber ());
      newMsg.setSender (topic);
      sendService.sendMessage (currentMsg.getSender (), newMsg);
    }
    return buf.toString ();
  }

  /**
   * The core application logic for game of 3. 
   * @param current is number that is being played on
   * @return is the next number
   * @throws InvalidInputException if input is less than zero
   */
  public NextNumber getNextNumber (int current) throws InvalidInputException {

    if (current <= 0) {
      throw new InvalidInputException ();
    }
    int reminder = current % 3;
    NextNumber ans = null;
    switch (reminder) {
      case 0:
        int ret = current / 3;
        ans = new NextNumber (0, ret);
        break;
      case 2:
        ret = (current + 1) / 3;
        ans = new NextNumber (1, ret);
        break;
      case 1:
        ret = (current - 1) / 3;
        ans = new NextNumber (-1, ret);
        break;
    }
    return ans;
  }

}
