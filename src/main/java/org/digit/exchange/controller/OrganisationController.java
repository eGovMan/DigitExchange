package org.digit.exchange.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.digit.exchange.constant.OrganisationRole;
import org.digit.exchange.dto.OrganisationDTO;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.exceptions.ResourceNotFoundException;
import org.digit.exchange.service.OrganisationService;
import org.digit.exchange.model.Organisation;
import org.digit.exchange.model.SearchRequest;
import org.springframework.data.domain.Page;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/exchange/v1")
public class OrganisationController {
    private final OrganisationService organisationService;
    // private static final Logger logger = LoggerFactory.getLogger(OrganisationController.class);

    public OrganisationController(OrganisationService organisationService){
        this.organisationService = organisationService;
    }

    @RequestMapping(value = "/admin/agency/create", method = RequestMethod.POST)
    public ResponseEntity<OrganisationDTO> createOrganisation(@RequestBody OrganisationDTO organisationDto) {
        try {
            Organisation result = organisationService.createOrganisation(organisationDto.toOrganisation());
            return ResponseEntity.ok(new OrganisationDTO(result));            
        } catch (ResourceNotFoundException e) {
            throw new CustomException("Error saving organisation", e);
        }
    }

    @RequestMapping(value = "/admin/agency/update", method = RequestMethod.POST)
    public ResponseEntity<OrganisationDTO> updateOrganisation(@RequestBody OrganisationDTO organisationDto) {
    try {
        Organisation result = organisationService.updateOrganisation(organisationDto.toOrganisation());
        return ResponseEntity.ok(new OrganisationDTO(result));            
    } catch (ResourceNotFoundException e) {
        throw new CustomException("Error saving organisation", e);
    }
    }

    @RequestMapping(value = "/admin/agency/get", method = RequestMethod.POST)
    public ResponseEntity<OrganisationDTO> get(@RequestBody String organisationId) {
        try {
            Optional<Organisation> result = organisationService.getOrganisationById(organisationId);
            if(result.isPresent()){
                return ResponseEntity.ok(new OrganisationDTO(result.get()));            
            }else{
                return ResponseEntity.notFound().build();
            }
            
        } catch (ResourceNotFoundException e) {
            throw new CustomException("Error getting organisation", e);
        }
    }

    @RequestMapping(value = "/agency/getbyadminid", method = RequestMethod.POST)
    public ResponseEntity<Page<OrganisationDTO>> getByAdminId(@RequestBody SearchRequest searchRequest) {
        try {
            Page<Organisation> result = organisationService.getByAdminId(searchRequest);
            Page<OrganisationDTO> orgDto = result.map( organisation->new OrganisationDTO(organisation));
            return ResponseEntity.ok(orgDto);            
        } catch (ResourceNotFoundException e) {
            throw new CustomException("Error getting organisation", e);
        }
    }


    @RequestMapping(value = "/agency/search", method = RequestMethod.POST)
    public ResponseEntity<Page<OrganisationDTO>> searchAgencyList(@Valid @RequestBody SearchRequest searchRequest) {
        try {
            Page<Organisation> result = organisationService.findAllOrganisations(searchRequest);
            // Convert each Organisation to OrganisationDTO
            Page<OrganisationDTO> dtoPage = result.map( organisation->new OrganisationDTO(organisation));
            return ResponseEntity.ok(dtoPage);
        } catch (ResourceNotFoundException e) {
            throw new CustomException("Error searching Organisations", e);
        }
    }

    @RequestMapping(value = "/agency/messagetypes", method = RequestMethod.POST)
    public ResponseEntity<List<String>> getMessageTypesEntity(@RequestBody String organisationId) {
        try {
            Optional<Organisation> result = organisationService.getOrganisationById(organisationId);
            if(result.isPresent()){
                List<String> messageTypes = getMessageTypesBasedOnRole(result.get().getOrgRoles());
                return ResponseEntity.ok(messageTypes);            
            }else{
                return ResponseEntity.notFound().build();
            }
            
        } catch (ResourceNotFoundException e) {
            throw new CustomException("Error getting organisation", e);
        }
    }

    private List<String> getMessageTypesBasedOnRole(Collection<OrganisationRole> roles) {
        // Assuming OrganisationRole is an enum
        if (roles.contains(OrganisationRole.IMPLEMENTING_AGENCY)) {
            return Arrays.asList("Program", "Estimate", "Sanction", "Allocation", "Disbursement");
        }

        // Add conditions for other roles if needed
        return Collections.emptyList();
    }

}
