package org.example.licensediscovery.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.licensediscovery.producer.MessageProducer;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("v1/organizations/{organizationId}/license")
public class KafkaController {
    private final MessageProducer messageProducer;
    public KafkaController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

//    @GetMapping("/send/{message}")
//    public void sendMessage(@PathVariable String organizationId, @PathVariable String message) {
//        messageProducer.sendMessage("microservice_topic", message);
//        System.out.println("Message sent to " + organizationId + ": " + message);
//    }

    @PostMapping("/send")
    public void sendMessage(@PathVariable String organizationId, @RequestBody JsonNode messageRequest) throws IOException {
        String key = messageRequest.get("key").asText();
        String message = messageRequest.get("message").asText();
        messageProducer.sendMessage("microservice_topic", key, message);
        System.out.println("Message sent to " + organizationId + ": " + message);
    }
}
