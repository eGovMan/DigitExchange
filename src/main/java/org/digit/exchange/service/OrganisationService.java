package org.digit.exchange.service;

import java.util.Optional;

import org.digit.exchange.model.Organisation;
import org.digit.exchange.repository.OrganisationRepository;
import org.springframework.stereotype.Service;

@Service
public class OrganisationService {

    private final OrganisationRepository organisationRepository;

    public OrganisationService(OrganisationRepository organisationRepository){
        this.organisationRepository = organisationRepository;
    }

    // Create
    public Organisation createOrganisation(Organisation organisation) {
        return organisationRepository.save(organisation);
    }

    // Read
    public Optional<Organisation> getOrganisationById(String id) {
        return organisationRepository.findById(id);
    }

    // Update
    public Organisation updateOrganisation(Organisation organisation) {
        // Assuming the organisation has a valid ID
        return organisationRepository.save(organisation);
    }

    // Delete
    public void deleteOrganisation(String id) {
        organisationRepository.deleteById(id);
    }
    
}
