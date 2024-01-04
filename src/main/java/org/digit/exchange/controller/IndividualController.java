package org.digit.exchange.controller;

import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.exceptions.ResourceNotFoundException;
import org.digit.exchange.model.messages.Individual;
import org.digit.exchange.service.IndividualService;
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
    public ResponseEntity<Individual> create(@RequestBody Individual user) {
        try {                
            org.digit.exchange.model.Individual individual = new org.digit.exchange.model.Individual();
            individual.setAddress(user.getAddress());
            individual.setId(user.getId());
            individual.setEmail(user.getEmail());
            individual.setName(user.getName());
            individual.setRoles(user.getRoles());
            individual.setPhone(user.getPhone());
            individual.setIsActive(false);
            service.createIndividual(individual);
            return ResponseEntity.ok(user);            
        } catch (ResourceNotFoundException e) {
            throw new CustomException("Error saving user", e);
        }
    }

    @RequestMapping(value = "/admin/individual/update", method = RequestMethod.POST)
    public ResponseEntity<Individual> update(@RequestBody Individual user) {
        try {                
            org.digit.exchange.model.Individual individual = new org.digit.exchange.model.Individual();
            individual.setId(user.getId());
            individual.setIsActive(true);
            service.updateIndividual(individual);
            return ResponseEntity.ok(user);            
        } catch (ResourceNotFoundException e) {
            throw new CustomException("Error saving user", e);
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
