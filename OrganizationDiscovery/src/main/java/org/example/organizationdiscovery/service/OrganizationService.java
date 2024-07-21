package org.example.organizationdiscovery.service;

import org.example.organizationdiscovery.model.Organization;
import org.example.organizationdiscovery.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Optional<Organization> getOrg(String org_id) {
        return organizationRepository.findById(org_id);
    }

    public void saveOrg(Organization org){
        org.setId( UUID.randomUUID().toString());
        organizationRepository.save(org);

    }

    public void updateOrg(Organization org){
        organizationRepository.save(org);
    }

    public void deleteOrg(Organization org){
        organizationRepository.deleteById(org.getId());
    }
}
