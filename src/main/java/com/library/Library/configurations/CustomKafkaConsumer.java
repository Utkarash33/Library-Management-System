package com.library.Library.configurations;


import com.library.Library.repository.RedisRepo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

@Component
public class CustomKafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(CustomKafkaConsumer.class);

    @Autowired
    RedisRepo redisRepo;



    @PostConstruct
    public void startConsumer() {
        Thread consumerThread = new Thread(this::consumeMessages);
        consumerThread.start();
    }


    private KafkaConsumer<String, String> kafkaConsumer;

    public CustomKafkaConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "book-event-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Arrays.asList("my-kafka-topic"));

    }

     void consumeMessages() {
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                processBookEvent(record.value());
            }
        }

    }

    private void processBookEvent(String value) {

       if(value.equals("return"))
       {
           redisRepo.incremetReturnCount();
       }
       else
       {
           redisRepo.incremetReserveCount();
       }

    }

    @PreDestroy
    private void close() {
        kafkaConsumer.close();
    }


}
