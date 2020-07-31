
### allgemeine Kafka Sachen
- [Verständnis von Listener advertised und witeres](https://www.confluent.io/blog/kafka-listeners-explained/)  
- [wurstmeister docker wiki mit Erklärung zur connectivity](https://github.com/wurstmeister/kafka-docker/wiki/Connectivity)  


###Zugriff auf memobase Kafkacluster
ssh -L 5000:mb-ka1.memobase.unibas.ch:9200 swissbib@mb-ka1.memobase.unibas.ch
im eigenen client localhost:5000


###Tests mit kafkacat
- [kafkacat bei confluent](https://docs.confluent.io/current/app-development/kafkacat-usage.html)  
- [Spielen mit timestamps](http://blog.mitchseymour.com/favorite-kafkacat-cmds/)
  - ```bash
    # get a timestamp (epoch in ms) for x minutes ago
    minago () {
        echo $(($(date +"%s000 - ($1 * 60000)")))
    }
    # diese bash Funktion kann dann wie folgt aufgerufen werden
    kafkacat -Q -b localhost:9092 -tcc-idssg:0:$(minago 20)
    
    #mit kafkacat docker container
    docker run -it  --network=host edenhill/kafkacat:1.6.0 -Q -b  localhost    -t cc-idssg:0:$(minago 39)
    ```
   - [githubseite von kafkacat](https://github.com/edenhill/kafkacat)
   - [umgang mit timestamps in Python](https://www.programiz.com/python-programming/datetime/timestamp-datetime)  

###benutztes docker-compose file (von Flink)

```yaml
version: '2'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    ports:
      - "2181:2181"
  kafka:
    image: wurstmeister/kafka:2.12-2.2.1
    ports:
      - "9092:9092"
    depends_on:
      - zookeeper
    environment:
      KAFKA_ADVERTISED_HOST_NAME: "kafka"
      KAFKA_ADVERTISED_PORT: "9092"
      HOSTNAME_COMMAND: "route -n | awk '/UG[ \t]/{print $$2}'"
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      #KAFKA_CREATE_TOPICS: "Rides:1:1, Fares:1:1, DriverChanges:1:1"
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
```
```bash
#Umgang mit der Netzwerkfunktionalität von Kafka
#wichtig (bei dem compose file oben): 
#in /etc/hosts bracht es den Eintrag
127.0.0.1       kafka
```

### Beispiele für den Umgang mit Kafka auf Producer und Consumer Ebene
[Kafka Spring](https://docs.spring.io/spring-kafka/docs/2.1.x/reference/html/_reference.html#kafka)  
l  
