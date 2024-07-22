package org.example.licensediscovery.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

//    @Value("${spring.kafka.consumer.group-id}")
//    private String groupIds;

    @KafkaListener(topics = "microservice_topic", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        System.out.println(message);
    }
}
