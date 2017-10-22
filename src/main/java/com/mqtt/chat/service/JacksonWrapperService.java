
package com.mqtt.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

@Service
public class JacksonWrapperService {

  ObjectMapper mapper = new ObjectMapper ();

  public ObjectMapper getMapper () {

    return mapper;
  }

}
