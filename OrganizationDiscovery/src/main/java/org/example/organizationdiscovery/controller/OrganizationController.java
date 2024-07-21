package org.example.organizationdiscovery.controller;

import org.example.organizationdiscovery.model.Organization;
import org.example.organizationdiscovery.service.OrganizationService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/{org_id}")
    public Optional<Organization> getOrganization(@PathVariable String org_id) {
        return organizationService.getOrg(org_id);
    }

    @PutMapping("/{org_id}")
    public void updateOrganization(@RequestBody Organization org) {
        organizationService.updateOrg(org);
    }

    @PostMapping("/{org_id}")
    public void addOrganization(@RequestBody Organization org) {
        organizationService.saveOrg(org);
    }

    // Delete method later
}
