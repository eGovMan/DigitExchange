package org.digit.exchange.controller;

import java.util.Optional;

import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.exceptions.ResourceNotFoundException;
import org.digit.exchange.service.OrganisationService;
import org.digit.exchange.model.Organisation;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/exchange/v1")
public class OrganisationController {
    private final OrganisationService service;
    // private static final Logger logger = LoggerFactory.getLogger(OrganisationController.class);

    public OrganisationController(OrganisationService service){
        this.service = service;
    }

    @RequestMapping(value = "/admin/organisation/create", method = RequestMethod.POST)
    public ResponseEntity<Organisation> createOrganisation(@RequestBody Organisation account) {
        try {
            Organisation result = service.createOrganisation(account);
            return ResponseEntity.ok(result);            
        } catch (ResourceNotFoundException e) {
            throw new CustomException("Error saving identity", e);
        }
    }

    @RequestMapping(value = "/admin/organisation/get", method = RequestMethod.POST)
    public ResponseEntity<Optional<Organisation>> get(@RequestBody String accountId) {
        try {
            Optional<Organisation> result = service.getOrganisationById(accountId);
            return ResponseEntity.ok(result);            
        } catch (ResourceNotFoundException e) {
            throw new CustomException("Error retrieving account", e);
        }
    }
    
}
