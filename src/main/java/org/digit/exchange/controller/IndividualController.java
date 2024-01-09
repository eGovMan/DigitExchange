package org.digit.exchange.controller;

import org.digit.exchange.dto.IndividualDTO;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.exceptions.ResourceNotFoundException;
import org.digit.exchange.model.SearchRequest;
import org.digit.exchange.model.Individual;
import org.digit.exchange.service.IndividualService;
import org.springframework.data.domain.Page;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("exchange/v1")
public class IndividualController {

    private final IndividualService service;
    // private static final Logger logger = LoggerFactory.getLogger(IndividualController.class);

    public IndividualController(IndividualService service){
        this.service = service;
    }

    @RequestMapping(value = "/public/individual/create", method = RequestMethod.POST)
    public ResponseEntity<IndividualDTO> create(@RequestBody IndividualDTO individualDto) {
        try {                
            Individual individual = individualDto.toIndividual();
            Individual result = service.createIndividual(individual);
            return ResponseEntity.ok(new IndividualDTO(result));            
        } catch (ResourceNotFoundException e) {
            throw new CustomException("Error saving user", e);
        }
    }

    @RequestMapping(value = "/admin/individual/update", method = RequestMethod.POST)
    public ResponseEntity<IndividualDTO> update(@RequestBody IndividualDTO individualDto) {
        try {                
            Individual individual = individualDto.toIndividual();
            Individual result = service.updateIndividual(individual);
            return ResponseEntity.ok(new IndividualDTO(result));            
        } catch (ResourceNotFoundException e) {
            throw new CustomException("Error saving user", e);
        }
    }

    @RequestMapping(value = "/admin/individual/search", method = RequestMethod.POST)
    public ResponseEntity<Page<Individual>> search(@RequestBody SearchRequest searchRequest) {
        try {
            Page<Individual> result = service.findAllIndividuals(searchRequest);
            return ResponseEntity.ok(result);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    // @RequestMapping(value = "/get", method = RequestMethod.POST)
    // public ResponseEntity<Optional<User>> get(@RequestBody String userId) {
    //     try {
    //         Optional<User> result = service.getUserById(userId);
    //         return ResponseEntity.ok(result);            
    //     } catch (ResourceNotFoundException e) {
    //         throw new CustomException("Error retrieving user", e);
    //     }
    // }
    
}
