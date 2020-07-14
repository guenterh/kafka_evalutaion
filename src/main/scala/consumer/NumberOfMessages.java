package consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;

public class NumberOfMessages {

    public static void main (String[] args){
        Properties props = new Properties();
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "12345");



        try (final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList("cc-idssg"));
            Set<TopicPartition> assignment;
            while ((assignment = consumer.assignment()).isEmpty()) {
                consumer.poll(Duration.ofMillis(100));
            }
            final Map<TopicPartition, Long> endOffsets = consumer.endOffsets(assignment);
            final Map<TopicPartition, Long> beginningOffsets = consumer.beginningOffsets(assignment);
            assert (endOffsets.size() == beginningOffsets.size());
            assert (endOffsets.keySet().equals(beginningOffsets.keySet()));

            Long totalCount = beginningOffsets.entrySet().stream().mapToLong(entry -> {
                TopicPartition tp = entry.getKey();
                Long beginningOffset = entry.getValue();
                Long endOffset = endOffsets.get(tp);
                return endOffset - beginningOffset;
            }).sum();
            System.out.println(totalCount);
        }
    }



}
