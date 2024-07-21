package org.example.licensediscovery.client;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.example.licensediscovery.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrganizationDiscoveryClient {

    // This is one way of doing it
    // This is not netflix eureka discovery client :)
    /*
    private final DiscoveryClient discoveryClient;
    public OrganizationDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public Organization getOrganization(String organizationId) {
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instance = discoveryClient.getInstances("OrganizationDiscovery");
        if (instance == null || instance.isEmpty()) return null;
        String url = String.format("http://%s/v1/organizations/%s",
                instance.get(0).getUri().toString(), organizationId);

        ResponseEntity<Organization> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Organization.class,
                organizationId
        );

        return response.getBody();

     */

        // The other way - using Rest Template
    private final RestTemplate restTemplate;

    public OrganizationDiscoveryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Organization getOrganization(String organizationId) {
        try {
            ResponseEntity<Organization> restExchange = restTemplate.exchange(
                    "http://api-gateway/organization/v1/organizations/{organizationId}",
                    HttpMethod.GET,
                    null,
                    Organization.class,
                    organizationId
            );

            return restExchange.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }


}

