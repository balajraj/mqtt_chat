# The chat application game of 3

The chat application has been implemented using mqtt protocol. The main motivation to choose mqtt is because the inspiration from facebook,they have used it for implementing the messenger service. The mqtt protocol is extremely light weight originated by the need to support telemetry for IOT devices.

The broker for mqtt implements the topic based pub/sub for communication between the clients. There are different quality of service can be selected depending on the user needs.

The mosquitto broker has been used for testing the client application. The broker can be downloaded from [here.](https://mosquitto.org/)

Run the broker using the following command in linux type of system, for windows please check the documentation to run the broker in mosquitto's website. The broker will listen for client connection on the default port 1883. 
```
/usr/local/sbin/mosquitto
```

The mosquitto broker does support quality of service 2 which means the messages are delivered exactly once and also messages are persisted in the broker if there are no clients to listen to a given topic and message are delivered when the client connects. This is totally configurable if no persistence is required then clean session can be used.

There are number of configuration that can be changed in the properties file. 

```
broker=tcp://localhost:1883
clientid=12323424343
topic=germany/berlin/ernst
qos=2
retained=true
clean=false
poolsize=10
```

The retained variable specifies the messages to retained in the broker and clean false means the seesion is stateful, so the delivery of the messsage can be guaranteed. The poolsize determines the thread pool size for processing message received from the broker.

To run the application compile the application using the command which will generate the fat jar. All the unit test cases will be run and coverage report is available in the target directory.
```
mvn clean package
```
Run the first client using the following command. 
```
java -jar -Dconfigfile=src/main/resources/config.properties target/chat-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

Run the second client using the following command. Please note the second client's properties file is different from the first client. To test the persistence please start first client send out a message and then start the second client which can prove the message persistence works fine with mqtt protocol broker based on the qos selected. 

```
java -jar -Dconfigfile=src/main/resources/config1.properties target/chat-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```
There is no need to create the topics explicity with mqtt broker and it is implicit create, meaning if the topic does not exist it will get created. 

The game can be started in the command line as follows, and here is the output of the game. 

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


# Design considerations
   The doman driven design pattern has been followed in the implementation of the code. Spring framework has been used for dependency injection all the new of objects has been avoided, only exceptions are entity object and broker client. The other design patterns that are used namely include command pattern , chain of responsibility and singleton. The api between the client for communication is based on json messages through the broker. There is no state maintained in the client application hence it is stateless. The broker maintains state for persisting message, but that is vendor's implementation of mqtt standard.  

   The code allows simulataneous games between two users this decision was made to keep the complexity less hence it is frees up the client app to maintain any state. 

   There are massively scalable mqtt brokers such as EMQ available hence scaling the application should not be a problem. Also the mqtt broker topic names include directory kind of structure. In the example shown there are two users in the city germany/berlin. If only germany/berlin is used the message will be boardcasted to all the users in the city berlin, if country alone is used the message will be boardcasted throughout the country. The above boardcast feature is not used in the code, but mqtt provides a generic way to distribute the message among client which can be advantage for other future requirements. 

The client will not be able initiate the game with oneself and proper message will be shown for the same. 
