# The chat application game of 3

The chat appliction has been implemented using mqtt protocol. The main motivation to choose mqtt is because the inspiration from facebook to have used it for implementing the messenger service based on it. The mqtt protocol is extremely light weight originated by the need to support telemetry support for IOT devices.

The broker for mqtt implements the topic based pub/sub for communication between the client. There are different quality of service can be selected depending on the user needs.

The mosquitto broker has been used for testing the application. The broker can be downloaded from [here](https://mosquitto.org/)

Run the broker using the following command in linux type of system, for windows please check the documentation to run the broker in the above website. The broker will listen for client connection on the default port 1883. 
```
/usr/local/sbin/mosquitto
```

The mosquitto broker does support quality of service 2 which means the messages are delivered exactly once and also messages are persisted in the broker if there are no clients to listen to a given topic and message are delivered when the client connects. 

There are number of configuration that can be changed in the properties file. 

```
broker=tcp://localhost:1883
clientid=12323424343
topic=germany/berlin/ernst
qos=2
retained=true
clean=false
```

The retained variable specifies the messages to retained in the broker and clean false means the seesion is stateful, so the delivery of the messsage can be guaranteed. 

To run the application compile the application using the command which will generate the fat jar. 
```
mvn clean package
```
Run the first client using the following command. 
```
java -jar -Dconfigfile=src/main/resources/config1.properties target/chat-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

Run the second client using the following command. Please note the second client's properties file is different from the first client. To test the persistence please start first client send out a message and then start the second client which can prove the message persistence works fine with mqtt protocol broker based on the qos selected. 

```
java -jar -Dconfigfile=src/main/resources/config.properties target/chat-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

The game can be started in the command line as follows, and here is the output of the game. If the second client is not started when the game began the message will be delivered to the client when it comes online. 

The output in the first client
```
SEND germany/berlin/muller 56
[germany/berlin/ernst playing with germany/berlin/muller]
[CURRENT NUM:19 NEXT NUM:6 ADDING: -1]

[germany/berlin/ernst playing with germany/berlin/muller]
[CURRENT NUM:2 NEXT NUM:1 ADDING: 1]
[germany/berlin/ernst is the WINNER]
```

The output in the second client. 
```
[germany/berlin/muller playing with germany/berlin/ernst]
[CURRENT NUM:56 NEXT NUM:19 ADDING: 1]

[germany/berlin/muller playing with germany/berlin/ernst]
[CURRENT NUM:6 NEXT NUM:2 ADDING: 0]
```

If the client stay ideal for long will get the following message printed in the console. 
```
SEVERE: 12323424334: Timed out as no write activity, keepAlive=60,000 lastOutboundActivity=1,508,651,601,198 lastInboundActivity=1,508,651,601,198 time=1,508,653,122,750 lastPing=1,508,651,601,198
```

# Design considerations
   The doman driven design pattern has been followed in the implementation of the code. The other design pattern that used in the code include command pattern and singleton. The api between the client for communication is based on json messages through the broker. There is no state maintained in the application hence it is stateless. 

   There were some consideration while writing the code, when two clients are involved in the game should another simulataneous game to be allowed, right now it is allowed, this is to reduce the complexity in the code to maintain states. 

   There are massively scalable mqtt brokers such as EMQ available hence scaling the application should not be a problem. Also the mqtt broker topic names include directory kind of structure. In the example shown there are two users in the city germany/berlin. If only germany/berlin is used the message will be boardcasted to all the users in the city berlin, if country alone is used the message will be boardcasted thoroughout the country. The above boardcast feature is not used in the code, but mqtt provides a generic way to distribute the message among client which can be advantage for other future requirements. 
