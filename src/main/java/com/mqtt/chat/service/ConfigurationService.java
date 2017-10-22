
package com.mqtt.chat.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mqtt.chat.Constants;

@Service
public class ConfigurationService {

  private static final Logger logger = LoggerFactory.getLogger (ConfigurationService.class);

  private InputStream input = null;
  private Properties prop = null;

  public ConfigurationService () {
    String configFile = System.getProperty (Constants.configfile);
    if (configFile == null) {
      logger.error ("Failed to read configFile exciting");
      System.exit (0);
    }
    try {
      input = new FileInputStream (configFile);
      prop = new Properties ();
      prop.load (input);
    } catch (IOException ex) {
      logger.error ("error in reading the config file", ex);
    } finally {
      if (input != null) {
        try {
          input.close ();
        } catch (IOException e) {
          logger.error ("error in closing the config file", e);
        }
      }
    }
  }

  /**
   * @param name of the parameter that needs to be obtained
   */
  public String getProperty (String name) {

    return prop.getProperty (name);
  }
}
