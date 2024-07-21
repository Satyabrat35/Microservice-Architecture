package org.example.licensediscovery.service;

import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.ws.rs.BadRequestException;
import org.example.licensediscovery.client.OrganizationDiscoveryClient;
import org.example.licensediscovery.config.ServiceConfig;
import org.example.licensediscovery.model.License;
import org.example.licensediscovery.model.Organization;
import org.example.licensediscovery.repository.LicenseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class LicenseService {
    private final LicenseRepository licenseRepository;
    private final ServiceConfig config;
    private final OrganizationDiscoveryClient organizationDiscoveryClient;
    public LicenseService(LicenseRepository licenseRepository, ServiceConfig config, OrganizationDiscoveryClient organizationDiscoveryClient) {
        this.licenseRepository = licenseRepository;
        this.config = config;
        this.organizationDiscoveryClient = organizationDiscoveryClient;
    }


    private Organization retrieveOrgInfo(String organizationId, String clientType) {
        Organization org = organizationDiscoveryClient.getOrganization(organizationId);
        return org;
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        Organization org = retrieveOrgInfo(organizationId, clientType);
        return license
                .withOrganizationName(org.getName())
                .withContactName(org.getContactName())
                .withContactEmail(org.getContactEmail())
                .withContactPhone(org.getContactPhone())
                .withComment(config.getExampleProperty());
    }

    // For Circuit breaker fall back
    private void randomlyWrong() {
        Random random = new Random();
        int randomNum = random.nextInt(3) + 1;
        // System.out.println(randomNum);
        if (randomNum == 3) {
            // System.out.println("Randomly Wrong");
             throw new RuntimeException("Simulated random failure");
        }
    }


    // Circuit Breaker pattern
    @CircuitBreaker(name="getlicensebyorg", fallbackMethod = "getLicensesByOrgFallback")
    public List<License> getLicensesByOrg(String organizationId) {
        randomlyWrong();
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public List<License> getLicensesByOrgFallback(String organizationId, Throwable throwable) {
        //System.out.println("Fallback exception " + throwable.getMessage());
        List<License> fallbackList = new ArrayList<>();
        License license = new License()
                .withId("00000-000-00000")
                .withOrganizationId(organizationId)
                .withProductName("Circuit Breaker - No licensing available");
        fallbackList.add(license);
        return fallbackList;
    }

    // Bulkhead pattern
    @Bulkhead(name="getparticularlicense", fallbackMethod = "getLicensesByOrgAndByLicenseFallback")
    public License getParticularLicense(String organizationId, String licenseId) {
        randomlyWrong();
        return licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
    }

    public License getLicensesByOrgAndByLicenseFallback(String organizationId, String licenseId, Throwable throwable) {
        return new License()
                .withId(licenseId)
                .withOrganizationId(organizationId)
                .withProductName("Bulkhead - No licensing available");
    }


//    public void saveLicense(License license) {
//        license.withId(UUID.randomUUID().toString());
//        licenseRepository.save(license);
//    }
//
//    public void updateLicense(License license) {
//        licenseRepository.save(license);
//    }

    // delete not reqd

}
