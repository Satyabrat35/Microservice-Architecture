package org.example.licensediscovery.service;

//import com.netflix.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscoveryService {
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;
    public DiscoveryService(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    public List getEurekaServices() {
        List<String> services = new ArrayList<String>();
        discoveryClient.getServices().forEach(serviceName ->{
            discoveryClient.getInstances(serviceName).forEach(instance -> {
                services.add(String.format("%s:%s", serviceName, instance.getUri()));
            });
        });
        return services;
    }

}
