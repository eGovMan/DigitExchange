package org.digit.exchange.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.digit.exchange.constant.Error;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.model.Individual;
import org.digit.exchange.model.Organisation;
import org.digit.exchange.model.SearchRequest;
import org.digit.exchange.model.ValidationException;
import org.digit.exchange.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OrganisationService {

    private final OrganisationRepository organisationRepository;
    private final IndividualService individualService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public OrganisationService(OrganisationRepository organisationRepository, IndividualService individualService){
        this.organisationRepository = organisationRepository;
        this.individualService = individualService;
    }

    // Create
    public Organisation createOrganisation(Organisation organisation) {
        List<String> validationErrors = new ArrayList<>();
        
        //verify if the adminId is registered as individual
        Optional<Individual> result = individualService.findIndividualById(organisation.getAdministratorId());
        if(result.isPresent()){
            Individual individual = result.get();
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                if(!userDetails.getUsername().equalsIgnoreCase(individual.getId())){
                    validationErrors.add(Error.INVALID_ADMIN_ID);
                }
            }
        }else{
            validationErrors.add(Error.INVALID_ADMIN_ID);
        }

        if (!validationErrors.isEmpty()) {
            throw new ValidationException(validationErrors);
        }
        //set the organisation is active as false.
        organisation.setIsActive(false);
        return organisationRepository.save(organisation);
    }

    // Read
    public Optional<Organisation> getOrganisationById(String id) {
        return organisationRepository.findById(id);
    }

    // Update
    public Organisation updateOrganisation(Organisation organisation) {
        //Assign agency api-key if not already assigned. 
        String apiKey = "";
        if(organisation.getApiKey() == null){
            apiKey = generateApiKey();
            //Encrypt the key
            organisation.setApiKey(passwordEncoder.encode(apiKey));        
        }
        Organisation result = organisationRepository.save(organisation);
        if(!apiKey.isEmpty()){
            //Since key has been generated, show the key to the user once.
            result.setApiKey(apiKey);
        }
        return result;
    }
    
    public Page<Organisation> findAllOrganisations(SearchRequest searchRequest) {
        try{
            int page = searchRequest.getPage();
            int size = searchRequest.getSize(); 
            Pageable pageable = PageRequest.of(page, size);
            
            Page<Organisation> result = organisationRepository.findAll(pageable);
            return result;
        } catch (Exception e) {
            throw new CustomException("Error finding organisation.", e);
        }
    }

    public boolean isValidApiKey(String apiKey) {
        if(organisationRepository.findByAgencyApiKey(apiKey) != null){
            return true;
        }
        return false;
    }

    public static String generateApiKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[24]; // 24 bytes = 192 bits
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public Page<Organisation> getByAdminId(SearchRequest searchRequest) {
        try{
            int page = searchRequest.getPage();
            int size = searchRequest.getSize(); 
            Pageable pageable = PageRequest.of(page, size);        
            return organisationRepository.findByAdministratorId(searchRequest.getSearchString(),pageable);
        } catch (Exception e) {
            throw new CustomException("Error finding organisation.", e);
        }
    }
    
}
