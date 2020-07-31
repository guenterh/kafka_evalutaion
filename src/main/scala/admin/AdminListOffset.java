package admin;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.TopicPartitionInfo;

import javax.script.SimpleScriptContext;
import java.util.Collections;
import java.util.Properties;
import java.util.stream.Collectors;

public class AdminListOffset {

    public static void main (String[] args) throws Exception {


        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        AdminClient admin = AdminClient.create(config);

        //checking if topic already exists
        boolean alreadyExists = admin.listTopics().names().get().stream()
                .anyMatch(existingTopicName -> existingTopicName.equals("cc-bla"));
        if (alreadyExists) {
            System.out.printf("topic already exits: %s%n", "cc-idssg");
        } else {
            //creating new topic
            System.out.printf("creating topic: %s%n", "cc-idssg");
            NewTopic newTopic = new NewTopic("cc-bla", 3, (short) 1);
            admin.createTopics(Collections.singleton(newTopic)).all().get();
        }

        //describing
        System.out.println("-- describing topic --");
        admin.describeTopics(Collections.singleton("cc-idssg")).all().get()
                .forEach((topic, desc) -> {
                    System.out.println("Topic: " + topic);


                    System.out.printf("Partitions: %s, partition ids: %s%n", desc.partitions().size(),
                            desc.partitions()
                                    .stream()
                                    .map(p ->    Integer.toString(p.partition()))
                                    .collect(Collectors.joining(",")));
                });

        admin.close();

    }





}
