package org.example.licensediscovery.producer;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {
    @Autowired
    private KafkaTemplate<String, String> kakfaTemplate;

    public void sendMessage(String topic, String key, String message) {
        kakfaTemplate.send(topic, key, message);
    }
}
