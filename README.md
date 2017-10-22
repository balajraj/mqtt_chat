# The chat application game of 3

The chat appliction has been implemented using mqtt protocol. The main motivation to choose mqtt is because the inspiration from facebook to have used it for implementing the messenger service based on it. 

The mosquitto broker has been used for testing the application. The broker can be downloaded from [here](https://mosquitto.org/)

Run the broker using the following command in linux type of system, for windows please check the documentation to run the broker in the above website. The broker will listen for client connection on the default port 1883. 
```
/usr/local/sbin/mosquitto
```

The mosquitto broker does support quality of service 2 which means the messages are delivered exactly once and also messages are persisted in the broker if there are no client to listen in a given topic. 

The mqtt is telemetry protocol which is extremely light weight and mainly used in the field of IOT where sensors send information using this protocol and has become popular in the recent times. 

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
Run the second client using the following command. Please not the second client's properties file is different from the first client. 
```
java -jar -Dconfigfile=src/main/resources/config.properties target/chat-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

The game can be started in the command line as follows, and here is the output of the game. If the second client is not started when the game began the message will be delivered to the client when it comes online. 

The output in the first client
```
SEND germany/berlin/muller 56
[CURRENT NUM:19 NEXT NUM:6 ADDING: -1]

[CURRENT NUM:2 NEXT NUM:1 ADDING: 1]
[germany/berlin/ernst] WINNER

```

The output in the second client. 
```
[CURRENT NUM:56 NEXT NUM:19 ADDING: 1]

[CURRENT NUM:6 NEXT NUM:2 ADDING: 0]
```

If the client stay ideal for long will get the following message printed in the console. 
```
SEVERE: 12323424334: Timed out as no write activity, keepAlive=60,000 lastOutboundActivity=1,508,651,601,198 lastInboundActivity=1,508,651,601,198 time=1,508,653,122,750 lastPing=1,508,651,601,198
```

