package org.example.licensediscovery.controller;

import org.example.licensediscovery.service.DiscoveryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/tools")
public class ToolsController {
    private final DiscoveryService discoveryService;
    public ToolsController(DiscoveryService discoveryService) {
        this.discoveryService = discoveryService;
    }

    @GetMapping("/eureka/services")
    public List<String> eurekaServices() {
        return discoveryService.getEurekaServices();
    }
}
