
package com.mqtt.chat;

import org.apache.log4j.FileAppender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

public class LogAppender {

  public static void init () {
    /*
     * ConsoleAppender console = new ConsoleAppender(); //create appender String PATTERN =
     * "%d [%p|%c|%C{1}] %m%n"; console.setLayout(new PatternLayout(PATTERN));
     * console.setThreshold(Level.DEBUG); console.activateOptions();
     * LogManager.getRootLogger().addAppender(console);
     */

    FileAppender fa = new FileAppender ();
    fa.setName ("FileLogger");
    fa.setFile ("chatapp.log");
    fa.setLayout (new PatternLayout ("%d %-5p [%c{1}] %m%n"));
    fa.setThreshold (Level.DEBUG);
    fa.setAppend (true);
    fa.activateOptions ();

    LogManager.getRootLogger ().addAppender (fa);

  }

}
