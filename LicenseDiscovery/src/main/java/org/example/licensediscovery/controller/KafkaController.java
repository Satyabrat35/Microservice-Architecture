package org.example.licensediscovery.controller;

import org.example.licensediscovery.producer.MessageProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/organizations/{organizationId}/license")
public class KafkaController {
    private final MessageProducer messageProducer;
    public KafkaController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @GetMapping("/send/{message}")
    public void sendMessage(@PathVariable String organizationId, @PathVariable String message) {
        messageProducer.sendMessage("microservice_topic", message);
        System.out.println("Message sent to " + organizationId + ": " + message);
    }
}
