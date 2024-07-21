package org.example.licensediscovery.controller;


import org.example.licensediscovery.model.License;
import org.example.licensediscovery.service.LicenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/organizations/{organizationId}/license")
public class LicenseServiceController {
    private final LicenseService licenseService;
    public LicenseServiceController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping("/")
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
        return licenseService.getLicensesByOrg(organizationId);
    }

    @GetMapping("/{licenseId}/{clientType}")
    public License getLicenseWithClient(@PathVariable String organizationId, @PathVariable String licenseId,
                                        @PathVariable String clientType) {
        // System.out.println("getLicenseWithClient");
        return licenseService.getLicense(organizationId, licenseId, clientType);
    }

    @GetMapping("/{licenseId}")
    public License getParticularLicense(@PathVariable("organizationId") String organizationId,
                                        @PathVariable String licenseId) {
        return licenseService.getParticularLicense(organizationId, licenseId);
    }



//    @GetMapping("/{licenseId}/{clientType}")
//    public String getLicenseWithClient(@PathVariable String organizationId, @PathVariable String licenseId,
//                                        @PathVariable String clientType) {
//        return "لمشتّتون كل قبل, عل وعُرفت لإنعدام اليابان، قبل.";
//        //return licenseService.getLicense(organizationId, licenseId, clientType);
//    }


}
