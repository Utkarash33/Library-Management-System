package com.library.Library.configurations;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.Properties;

@Component
@Slf4j
public class CustomKafkaProducer {



     public static void sendEvent(String value) {
         Properties props = new Properties();
         props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
         props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
         props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
         Producer<String,String> kafkaProducer= new KafkaProducer<>(props);
         kafkaProducer.send(new ProducerRecord<>("my-kafka-topic","count_event",value));
     }

}
