package com.mqtt.chat.service;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import com.mqtt.chat.Constants;

public class ConfigurationServerTest {
  
  @Before
  public void setup() {
    System.setProperty("configfile","src/main/resources/config.properties");
    
  }
  
  @Test
  public void testConfig() {
    ConfigurationService service = new ConfigurationService();
    assertTrue(service.getProperty (Constants.retained).equals ("true")); 
  }

}
